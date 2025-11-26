package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.CommandeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandeMapper {
    Commande toEntity(CommandeCreateRequestDto request);
    CommandeResponseDto toDTO(Commande entity);
}
