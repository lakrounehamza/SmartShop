package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem  toEntity(OrderItemCreateRequestDto request);
    OrderItemResponseDto toDTO(OrderItem entity);
}
