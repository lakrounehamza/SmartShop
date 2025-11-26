package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.VirementCreateRequestDto;
import com.youcode.SmartShop.dtos.response.VirementResponseDto;
import com.youcode.SmartShop.entity.Virement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VirementMapper {
    Virement  toEntity(VirementCreateRequestDto  request);
    VirementResponseDto toDTO(Virement  entity);
}
