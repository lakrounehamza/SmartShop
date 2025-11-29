package com.youcode.SmartShop.dtos.request;

import jakarta.validation.Valid;

import java.util.List;

public record CommandeCreateWithMultiItemRequestDto(
        @Valid
        CommandeCreateRequestDto commandeCreateRequestDto,
        @Valid
        List<OrderItemCreateRequestDto>  orderItemCreateRequestDtos
) {
}
