package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.request.CommandeCreateWithMultiItemRequestDto;
import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClinetStatisticResponseDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.enums.OrderStatus;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.CommandeMapper;
import com.youcode.SmartShop.repository.ClientRepository;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.service.interfaces.ICommandeService;
import com.youcode.SmartShop.service.interfaces.IOrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements ICommandeService {
    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final IOrderItemService orderItemService;

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
