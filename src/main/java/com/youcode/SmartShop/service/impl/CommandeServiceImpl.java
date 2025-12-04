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
import com.youcode.SmartShop.service.interfaces.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private final VirementRepository  virementRepository;
    private  final  EspecesRepository especesRepository;
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
        if(!clientRepository.existsById(id))
            throw new NotFoundException("client introuvable avec l'id "+id);
        return new ClinetStatisticResponseDto( commandeRepository.findCountByClinet_id(id),commandeRepository.findCumuleByClient_Id(id).get(), commandeRepository.findFirstDateByClient_id(id).get(), commandeRepository.findLastDateByClient_id(id).get());
    }
    @Override
        public CommandeResponseDto saveWithMultiOrderItem(CommandeCreateWithMultiItemRequestDto request){
            List<OrderItemCreateRequestDto> orderItemRequest = request.orderItemCreateRequestDtos();
            CommandeCreateRequestDto commandeRequest =  request.commandeCreateRequestDto();
            Commande   commande  = commandeMapper.toEntity(commandeRequest);
            if(commandeRepository.getCountCommandePending(commande.getClient().getId())>0)
                throw new IncorrectInputException("il existe des commandes non confirmees");
            BigDecimal prixTotal = orderItemRequest.stream()
                    .map(item -> {
                        BigDecimal prixProduit = productRepository.findById(item.produit_id())
                                .map(prod -> prod.getPrix())
                                .orElse(BigDecimal.ZERO);
                        return prixProduit.multiply(BigDecimal.valueOf(item.quantite()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            commande.setSousTotal(prixTotal);
            Client client = clientRepository.findById(commande.getClient().getId()).get();

            if(client.getNiveauFidelite().equals(CustomerTier.SILVER) && prixTotal.compareTo(BigDecimal.valueOf(500))==1)
                commande.setRemise(5);
            else if(client.getNiveauFidelite().equals(CustomerTier.GOLD) && prixTotal.compareTo(BigDecimal.valueOf(800))==1)
                commande.setRemise(10);
            else if(client.getNiveauFidelite().equals(CustomerTier.PLATINUM) && prixTotal.compareTo(BigDecimal.valueOf(1200))==1)
                commande.setRemise(15);
            Optional<CodePromo> codePromo  = codePromoRepository.findByCode(commande.getCodePromo());
            if(codePromo.isPresent()){
                CodePromo codePromo1 = codePromo.get();
                if(codePromo1.getLimit()>codePromo1.getNumberOfTimes())
                {
                    commande.setRemise(commande.getRemise()+5);
                    codePromo1.setNumberOfTimes(codePromo1.getNumberOfTimes()-1);
                    codePromoRepository.save(codePromo1);
                }
            }
        commande.setMontant_restant(prixTotal.subtract(prixTotal.multiply(BigDecimal.valueOf(commande.getRemise())).divide(BigDecimal.valueOf(100))));
        BigDecimal tva  = commande.getMontant_restant().multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100));
        commande.setMontant_restant(commande.getMontant_restant().add(tva));

                Commande commandSaved = commandeRepository.save(commande);
            List<OrderItem>   listItem   = orderItemRequest.stream().map(item ->{
                OrderItem entity = orderItemMapper.toEntity(item);
                Product  product = productRepository.findById(item.produit_id()).orElse(new Product(item.produit_id(),"not exist",BigDecimal.ZERO,0));
                BigDecimal totalline = BigDecimal.valueOf(item.quantite()).multiply(product.getPrix());
                entity.setTotalLigne(totalline);
                entity.setPrix(product.getPrix());
                entity.setProduct(product);
                entity.setCommande(commandSaved);

                return entity;
            }).collect(Collectors.toList());
             boolean confli = false;
             String  messageError = "";
            for  (OrderItem item : listItem) {
                try {
                    orderItemService.save(item);
                } catch (ProductStockUnavailableException | NotFoundException e) {
                    confli = true;
                   messageError += " "+e.getMessage();
                }
            }
            if(confli){
                commandSaved.setStatut(OrderStatus.REJECTED);
                commandeRepository.save(commandSaved);
                throw new CommandeCreationFailedException(messageError);
            }


            commandSaved.setAticles(listItem);
            return commandeMapper.toDTO(commandSaved);

        }
    @Override
    public CommandeResponseDto updateStatus(Long id,OrderStatus status) {
        if(!commandeRepository.existsById(id))
            throw new NotFoundException("commande introuvable avec l'id "+id);
        Commande commande = commandeRepository.findById(id).get();
        commande.setStatut(status);
        if(status.equals(OrderStatus.CONFIRMED) ){
            if( commande.getMontant_restant().compareTo(BigDecimal.ZERO)!=0)
                throw new IncorrectInputException("tu  veux confirme  un commande ne  pas   pay ");
            commande.getAticles().stream().forEach(orderItem -> {
                Product product =orderItem.getProduct();
                System.out.println(product.getStock());
                product.setStock(product.getStock()-orderItem.getQuantite());
                productRepository.save(product);
            });

            commande.getPaiement().stream().forEach(paiement -> {
                paiement.setStatut(PaymentStatus.ENCAISSE);
                if (paiement instanceof Cheque){
                    Cheque cheque = (Cheque) paiement;
                    chequeRepository.save(cheque);
                }else if(paiement  instanceof Virement)
                {
                    Virement   virement  = (Virement) paiement;
                    virementRepository.save(virement);
                }else{
                    Especes  especes  = (Especes)  paiement;
                    especesRepository.save(especes);
                }
            });

            commande.setTotal(commande.getTotal().add(commande.getSousTotal()));
            int countCommande = commandeRepository.findCountConfirmeByClinet_id(commande.getClient().getId());
            if(countCommande ==2 || commande.getTotal().compareTo(BigDecimal.valueOf(1000)) ==1)
            {
                Client client   = commande.getClient();
                client.setNiveauFidelite(CustomerTier.SILVER);
                clientRepository.save(client);
            } else if(countCommande ==9 || commande.getTotal().compareTo(BigDecimal.valueOf(5000)) ==1)
            {
                Client client   = commande.getClient();
                client.setNiveauFidelite(CustomerTier.GOLD);
                clientRepository.save(client);
            }else if(countCommande ==19 || commande.getTotal().compareTo(BigDecimal.valueOf(15000)) ==1)
            {
                Client client   = commande.getClient();
                client.setNiveauFidelite(CustomerTier.PLATINUM);
                clientRepository.save(client);
            }


            commande.getAticles().forEach(orderItem -> {

                Product produit = productRepository.findById(
                        orderItem.getProduct().getId()
                ).orElseThrow(() -> new IncorrectInputException("Produit introuvable"));
 List<OrderItem> itemsDuProduit =
                        orderItemRepository.findByProduct_Id(produit.getId());
  itemsDuProduit.forEach(item -> {

                    if (item.getQuantite() > produit.getStock()) {

                        Commande commandeAAnnuler = commandeRepository.findById(
                                item.getCommande().getId()
                        ).orElseThrow(() -> new IncorrectInputException("Commande introuvable"));

                        commandeAAnnuler.setStatut(OrderStatus.CANCELED);
                        commandeRepository.save(commandeAAnnuler);
                    }
                });
            });

        }
        else if(status.equals(OrderStatus.CANCELED))
        {
            commande.getPaiement().stream().map(paiement -> {
                paiement.setStatut(PaymentStatus.REJETE);
                if (paiement instanceof Cheque){
                    Cheque cheque = (Cheque) paiement;
                    chequeRepository.save(cheque);
                }else if(paiement  instanceof Virement)
                {
                    Virement   virement  = (Virement) paiement;
                    virementRepository.save(virement);
                }else{
                    Especes  especes  = (Especes)  paiement;
                    especesRepository.save(especes);
                }
                return   null;
            });
        }
        return commandeMapper.toDTO(commandeRepository.save(commande));
    }

}