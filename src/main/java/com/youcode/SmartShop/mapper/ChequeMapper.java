package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.ChequeCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ChequeResponseDto;
import com.youcode.SmartShop.entity.Cheque;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChequeMapper {
    Cheque toEntity(ChequeCreateRequestDto requestDto);
    ChequeResponseDto toDTO(Cheque entity);
}

