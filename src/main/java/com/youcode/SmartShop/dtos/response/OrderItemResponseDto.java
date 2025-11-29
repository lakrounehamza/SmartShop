package com.youcode.SmartShop.dtos.response;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponseDto(
        int quantite,
        double prix,
        BigDecimal totalLigne,
        //long commande_id,
        long produit_id) {
}
