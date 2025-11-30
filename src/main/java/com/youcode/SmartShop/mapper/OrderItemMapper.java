package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.OrderItemCreateRequestDto;
import com.youcode.SmartShop.dtos.response.OrderItemResponseDto;
import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "produit_id", target = "product")
    OrderItem toEntity(OrderItemCreateRequestDto request);
        OrderItemResponseDto toDTO(OrderItem entity);
        default Product fromId(Long id) {
        if (id == null) return null;
        Product p = new Product();
        p.setId(id);
        return p;
    }
}
