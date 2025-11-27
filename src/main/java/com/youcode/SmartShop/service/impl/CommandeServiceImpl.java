package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.CommandeMapper;
import com.youcode.SmartShop.repository.CommandeRepository;
import com.youcode.SmartShop.service.interfaces.ICommandeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements ICommandeService {
    private final CommandeMapper  commandeMapper;
    private final CommandeRepository  commandeRepository;
    @Override
    public CommandeResponseDto save(CommandeCreateRequestDto request) {
        Commande  commande  = commandeMapper.toEntity(request);
        Commande  commandSaved = commandeRepository.save(commande);
        return commandeMapper.toDTO(commandSaved);
    }

    @Override
    public CommandeResponseDto getById(Long id) {
        return commandeMapper.toDTO(commandeRepository.findById(id).orElseThrow( ()-> new  NotFoundException("Commande introuvable avec l'id "+id)));
    }

    @Override
    public Page<CommandeResponseDto> getByClientId(Long id,Pageable pageable) {
        Page<Commande>  commandes = commandeRepository.findByClient_Id(id,pageable);
        if(commandes.getTotalElements()<1)
            throw  new NotFoundException("aucun commande trouve");
        return commandes.map(commandeMapper::toDTO);
    }

    @Override
    public Page<CommandeResponseDto> getAll(Pageable pageable) {
        Page<Commande>  commandes = commandeRepository.findAll(pageable);
        if(commandes.getTotalElements()<1)
            throw  new NotFoundException("aucun commande trouve");
        return commandes.map(commandeMapper::toDTO);
    }
}
