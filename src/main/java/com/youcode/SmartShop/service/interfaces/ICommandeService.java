package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommandeService {
    CommandeResponseDto   save(CommandeCreateRequestDto request);
    CommandeResponseDto getById(Long id);
    Page<CommandeResponseDto>  getByClientId(Long id,Pageable pageable);
    Page<CommandeResponseDto>  getAll(Pageable pageable);
}
