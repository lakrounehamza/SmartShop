package com.youcode.SmartShop.mapper;

import com.youcode.SmartShop.dtos.request.ProduitCreateRequestDto;
import com.youcode.SmartShop.dtos.response.ProduitResponseDto;
import com.youcode.SmartShop.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProduitCreateRequestDto  request);
    ProduitResponseDto   toDTO(Product entity);
}
