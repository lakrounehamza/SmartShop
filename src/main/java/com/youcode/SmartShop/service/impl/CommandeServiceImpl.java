package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.request.CommandeCreateWithMultiItemRequestDto;
import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClinetStatisticResponseDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.*;
import com.youcode.SmartShop.enums.CustomerTier;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.enums.PaymentStatus;
import com.youcode.SmartShop.exception.CommandeCreationFailedException;
import com.youcode.SmartShop.exception.IncorrectInputException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.exception.ProductStockUnavailableException;
import com.youcode.SmartShop.mapper.CommandeMapper;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.*;
import com.youcode.SmartShop.service.interfaces.ICommandeService;
import com.youcode.SmartShop.service.interfaces.IOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements ICommandeService {

    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final IOrderItemService orderItemService;
    private final ProductRepository productRepository;
    private final OrderItemMapper orderItemMapper;
    private final ChequeRepository chequeRepository;
    private final VirementRepository virementRepository;
    private final EspecesRepository especesRepository;
    private final OrderItemRepository orderItemRepository;
    private final CodePromoRepository codePromoRepository;

    @Override
    public CommandeResponseDto save(CommandeCreateRequestDto request) {
        Commande commande = commandeMapper.toEntity(request);
        Commande commandSaved = commandeRepository.save(commande);
        return commandeMapper.toDTO(commandSaved);
    }

    @Override
    public CommandeResponseDto getById(Long id) {
        return commandeMapper.toDTO(commandeRepository.findById(id).orElseThrow(() -> new NotFoundException("Commande introuvable avec l'id " + id)));
    }

    @Override
    public Page<CommandeResponseDto> getByClientId(Long id, Pageable pageable) {
        Page<Commande> commandes = commandeRepository.findByClient_Id(id, pageable);
        if (commandes.getTotalElements() < 1)
            throw new NotFoundException("aucun commande trouve");
        return commandes.map(commandeMapper::toDTO);
    }

    @Override
    public Page<CommandeResponseDto> getAll(Pageable pageable) {
        Page<Commande> commandes = commandeRepository.findAll(pageable);
        if (commandes.isEmpty())
            throw new NotFoundException("aucun commande trouve");
        return commandes.map(commandeMapper::toDTO);
    }

    @Override
    public ClinetStatisticResponseDto getClientStatistic(Long id) {
        if (!clientRepository.existsById(id))
            throw new NotFoundException("client introuvable avec l'id " + id);
        if(commandeRepository.findCountByClinet_id(id)==0)
            return  new ClinetStatisticResponseDto(0,BigDecimal.ZERO,null,null);
        return new ClinetStatisticResponseDto(commandeRepository.findCountByClinet_id(id), commandeRepository.findCumuleByClient_Id(id).get(), commandeRepository.findFirstDateByClient_id(id).get(), commandeRepository.findLastDateByClient_id(id).get());
    }

    @Override
    public CommandeResponseDto saveWithMultiOrderItem(CommandeCreateWithMultiItemRequestDto request) {

        List<OrderItemCreateRequestDto> orderItemRequest = request.orderItemCreateRequestDtos();
        CommandeCreateRequestDto commandeRequest = request.commandeCreateRequestDto();
        Commande commande = commandeMapper.toEntity(commandeRequest);

        if (commandeRepository.getCountCommandePending(commande.getClient().getId()) > 0)
            throw new IncorrectInputException("il existe des commandes non confirmees");

        BigDecimal prixTotal = calculPrixCommande(orderItemRequest);

        commande.setSousTotal(prixTotal);
        calculRemise(commande);

        commande.setMontant_restant(prixTotal.subtract(prixTotal.multiply(BigDecimal.valueOf(commande.getRemise())).divide(BigDecimal.valueOf(100))));
        BigDecimal tva = commande.getMontant_restant().multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100));
        commande.setMontant_restant(commande.getMontant_restant().add(tva));

        Commande commandSaved = commandeRepository.save(commande);

        List<OrderItem> listItem = orderItemRequest.stream().map(item -> {
            OrderItem entity = orderItemMapper.toEntity(item);
            Product product = productRepository.findById(item.produit_id()).orElse(new Product(item.produit_id(), "not exist", BigDecimal.ZERO, 0,false));
            BigDecimal totalline = BigDecimal.valueOf(item.quantite()).multiply(product.getPrix());
            entity.setTotalLigne(totalline);
            entity.setPrix(product.getPrix());
            entity.setProduct(product);
            entity.setCommande(commandSaved);

            return entity;
        }).collect(Collectors.toList());

        boolean confli = false;
        String messageError = "";
        for (OrderItem item : listItem) {
            try {
                orderItemService.save(item);
            } catch (ProductStockUnavailableException | NotFoundException e) {
                confli = true;
                messageError += " " + e.getMessage();
            }
        }
        if (confli) {
            commandSaved.setStatut(OrderStatus.REJECTED);
            commandeRepository.save(commandSaved);
            throw new CommandeCreationFailedException(messageError);
        }


        commandSaved.setAticles(listItem);
        return commandeMapper.toDTO(commandSaved);

    }

    @Override
    public CommandeResponseDto updateStatus(Long id, OrderStatus status) {

        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Commande introuvable avec l'id " + id));

        commande.setStatut(status);

        if (status == OrderStatus.CONFIRMED) {
            handleConfirmation(commande);
        } else if (status == OrderStatus.CANCELED) {
            updatePaymentsStatus(commande, PaymentStatus.REJETE);
        }

        return commandeMapper.toDTO(commandeRepository.save(commande));
    }

    private BigDecimal calculPrixCommande(List<OrderItemCreateRequestDto> orderItemRequest) {

        return orderItemRequest.stream()
                .map(item -> productRepository.findById(item.produit_id())
                        .map(Product::getPrix)
                        .orElse(BigDecimal.ZERO)
                        .multiply(BigDecimal.valueOf(item.quantite()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void calculRemise(Commande commande) {

        Client client = clientRepository.findById(commande.getClient().getId())
                .orElseThrow(() -> new NotFoundException("Client introuvable"));

        BigDecimal total = commande.getSousTotal();
        int remise = 0;

        switch (client.getNiveauFidelite()) {
            case SILVER -> remise = total.compareTo(BigDecimal.valueOf(500)) > 0 ? 5 : 0;
            case GOLD -> remise = total.compareTo(BigDecimal.valueOf(800)) > 0 ? 10 : 0;
            case PLATINUM -> remise = total.compareTo(BigDecimal.valueOf(1200)) > 0 ? 15 : 0;
        }

         Optional<CodePromo> codePromo = codePromoRepository.findByCode(commande.getCodePromo());
                if(codePromo.isPresent()){
                   CodePromo  cp = codePromo.get();
                        if (cp.getLimit() > cp.getNumberOfTimes()) {
                            remise += 5;
                            cp.setNumberOfTimes(cp.getNumberOfTimes() + 1);
                            codePromoRepository.save(cp);
                        }
                }


        commande.setRemise(remise);
    }

    private void handleConfirmation(Commande commande) {

        if (commande.getMontant_restant().compareTo(BigDecimal.ZERO) != 0) {
            throw new IncorrectInputException("Impossible de confirmer : commande non totalement payée");
        }
        updateStock(commande);
        updatePaymentsStatus(commande, PaymentStatus.ENCAISSE);
        commande.setTotal(commande.getTotal().add(commande.getSousTotal()));
        updateCustomerFidelity(commande);
        checkForStockConflicts(commande);
    }
    private void updatePaymentsStatus(Commande commande, PaymentStatus statut) {
        commande.getPaiement().forEach(p -> {
            p.setStatut(statut);

            if (p instanceof Cheque) {
                chequeRepository.save((Cheque) p);
            } else if (p instanceof Virement) {
                virementRepository.save((Virement) p);
            } else {
                especesRepository.save((Especes) p);
            }
        });
    }
    private void updateStock(Commande commande) {
        for (OrderItem item : commande.getAticles()) {
            Product product = item.getProduct();
            int newStock = product.getStock() - item.getQuantite();

            if (newStock < 0)
                throw new IncorrectInputException("Stock insuffisant pour le produit " + product.getNom());

            product.setStock(newStock);
            productRepository.save(product);
        }
    }

    private void updateCustomerFidelity(Commande commande) {
        Client client = commande.getClient();
        int confirmedCount = commandeRepository.findCountConfirmeByClinet_id(client.getId());
        BigDecimal total = commande.getTotal();
        if (confirmedCount >= 19 || total.compareTo(BigDecimal.valueOf(15000)) > 0) {
            client.setNiveauFidelite(CustomerTier.PLATINUM);
        } else if (confirmedCount >= 9 || total.compareTo(BigDecimal.valueOf(5000)) > 0) {
            client.setNiveauFidelite(CustomerTier.GOLD);
        } else if (confirmedCount >= 2 || total.compareTo(BigDecimal.valueOf(1000)) > 0) {
            client.setNiveauFidelite(CustomerTier.SILVER);
        }

        clientRepository.save(client);
    }
    private void checkForStockConflicts(Commande commande) {

        for (OrderItem item : commande.getAticles()) {

            Product produit = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new IncorrectInputException("Produit introuvable"));

            List<OrderItem> itemsProduit = orderItemRepository.findByProduct_Id(produit.getId());

            itemsProduit.forEach(existingItem -> {
                if (existingItem.getQuantite() > produit.getStock()) {

                    Commande commandeAAnnuler = commandeRepository.findById(
                                    existingItem.getCommande().getId())
                            .orElseThrow(() -> new IncorrectInputException("Commande introuvable"));

                    commandeAAnnuler.setStatut(OrderStatus.CANCELED);
                    commandeRepository.save(commandeAAnnuler);
                }
            });
        }
    }

}