package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.request.CommandeCreateWithMultiItemRequestDto;
import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClinetStatisticResponseDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Product;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.exception.CommandeCreationFailedException;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.exception.ProductStockUnavailableException;
import com.youcode.SmartShop.mapper.CommandeMapper;
import com.youcode.SmartShop.mapper.OrderItemMapper;
import com.youcode.SmartShop.repository.ClientRepository;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.repository.ProductRepository;
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
        if (commandes.getTotalElements() < 1)
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
            BigDecimal prixTotal = orderItemRequest.stream()
                    .map(item -> {
                        BigDecimal prixProduit = productRepository.findById(item.produit_id())
                                .map(prod -> prod.getPrix())
                                .orElse(BigDecimal.ZERO);
                        return prixProduit.multiply(BigDecimal.valueOf(item.quantite()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            commande.setSousTotal(prixTotal);
            commande.setMontant_restant(prixTotal);
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
    public CommandeResponseDto updateStatus(Long id,OrderStatus status){
        if(commandeRepository.existsById(id))
            throw new NotFoundException("commande introuvable avec l'id "+id);
        Commande commande = commandeRepository.findById(id).get();
        commande.setStatut(status);
        return commandeMapper.toDTO(commandeRepository.save(commande));
    }
}