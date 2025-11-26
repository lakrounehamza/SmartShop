package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.EspecesCreateRequestDto;
import com.youcode.SmartShop.dtos.response.EspecesResponseDto;
import com.youcode.SmartShop.entity.Especes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EspecesMapper {
    Especes  toEntity(EspecesCreateRequestDto request);
    EspecesResponseDto toDTO(Especes entity);
}
