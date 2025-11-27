package com.youcode.SmartShop.service.interfaces;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;

public interface IClientService {
    ClientResponseDto save(ClientCreateRequestDto request);
    ClientResponseDto getClientById(Long id);
}
