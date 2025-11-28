package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.request.CommandeCreateWithMultiItemRequestDto;
import com.youcode.SmartShop.dtos.response.ClinetStatisticResponseDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommandeService {
    CommandeResponseDto   save(CommandeCreateRequestDto request);
    CommandeResponseDto getById(Long id);
    Page<CommandeResponseDto>  getByClientId(Long id,Pageable pageable);
    Page<CommandeResponseDto>  getAll(Pageable pageable);
    ClinetStatisticResponseDto getClientStatistic(Long id);
    CommandeResponseDto saveWithMultiOrderItem(CommandeCreateWithMultiItemRequestDto request);
    CommandeResponseDto updateStatus(Long id,OrderStatus status);
}
