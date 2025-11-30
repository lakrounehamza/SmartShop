package com.youcode.SmartShop.dtos.response;


import com.youcode.SmartShop.entity.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponseDto(
        long id,
        int quantite,
        BigDecimal prix,
        BigDecimal totalLigne,
        //long commande_id,
        Product product) {
}
