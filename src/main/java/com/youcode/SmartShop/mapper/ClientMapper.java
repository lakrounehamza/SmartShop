package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.ClientCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ClientResponseDto;
import com.youcode.SmartShop.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client  toEntity(ClientCreateRequestDto request);
    ClientResponseDto toDTO(Client clinet);
}
