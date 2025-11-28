package com.youcode.SmartShop.service.impl;

import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import com.youcode.SmartShop.entity.Especes;
import com.youcode.SmartShop.exception.NotFoundException;
import com.youcode.SmartShop.mapper.EspecesMapper;
import com.youcode.SmartShop.repository.EspecesRepository;
import com.youcode.SmartShop.service.interfaces.IEspecesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EspecesServiceImpl implements IEspecesService {

    private final EspecesMapper especesMapper;
    private final EspecesRepository especesRepository;

    @Override
    public EspecesResponseDto save(EspecesCreateRequestDto request) {
        Especes especes = especesMapper.toEntity(request);
        Especes especesSaved = especesRepository.save(especes);
        return especesMapper.toDTO(especesSaved);
    }

    @Override
    public EspecesResponseDto getById(Long id) {
        return especesMapper.toDTO(especesRepository.findById(id).orElseThrow(()->new NotFoundException("especes introuvable avec l'id "+id)));
    }

    @Override
    public Page<EspecesResponseDto> getAll(Pageable pageable) {
        Page<Especes>  especesPage  = especesRepository.findAll(pageable);
        if(especesPage.isEmpty())
            throw new NotFoundException("aucun especes trouve");
        return especesPage.map(especesMapper::toDTO);
    }

    @Override
    public Page<EspecesResponseDto> getByCommandeId(Long id, Pageable pageable) {
        Page<Especes>  especesPage  = especesRepository.findByCommande_Id(id,pageable);
        if(especesPage.isEmpty())
            throw new NotFoundException("aucun especes trouve");
        return especesPage.map(especesMapper::toDTO);
    }
}
