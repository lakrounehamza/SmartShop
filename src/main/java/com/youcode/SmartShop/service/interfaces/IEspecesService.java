package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEspecesService {
    EspecesResponseDto save(EspecesCreateRequestDto request);
    EspecesResponseDto getById(Long id);
    Page<EspecesResponseDto> getAll(Pageable pageable);
    Page<EspecesResponseDto> getByCommandeId(Long  id, Pageable pageable);
}
