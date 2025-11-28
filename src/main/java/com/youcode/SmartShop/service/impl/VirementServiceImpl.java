package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import com.youcode.SmartShop.entity.Virement;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.VirementMapper;
import com.youcode.SmartShop.repository.VirementRepository;
import com.youcode.SmartShop.service.interfaces.IVirementService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VirementServiceImpl implements IVirementService {
    private final VirementRepository  repository;
    private final VirementMapper  mapper;
    @Override
    public VirementResponseDto save(VirementCreateRequestDto request) {
        Virement virement  = mapper.toEntity(request);
        Virement virementSaved = repository.save(virement);
        return mapper.toDTO(virementSaved);
    }

    @Override
    public VirementResponseDto getById(Long id) {
        return  mapper.toDTO(repository.findById(id).orElseThrow(()->new NotFoundException("virement introuvable avec l'id"+id)));
    }

    @Override
    public Page<VirementResponseDto> getByCommabdeId(Long id, Pageable pageable) {
        Page<Virement>  virements  = repository.findByCommande_Id(id,pageable);
        if(virements.isEmpty())
            throw  new NotFoundException("aucun virement trouve");
        return virements.map(mapper::toDTO);    }

    @Override
    public Page<VirementResponseDto> getAll(Pageable pageable) {
        Page<Virement>  virements  = repository.findAll(pageable);
        if(virements.isEmpty())
            throw  new NotFoundException("aucun virement trouve");
        return virements.map(mapper::toDTO);
    }
}
