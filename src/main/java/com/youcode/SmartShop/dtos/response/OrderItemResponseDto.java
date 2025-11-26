package com.youcode.SmartShop.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderItemResponseDto {
    private int quantite;
    private double prix;
    private BigDecimal totalLigne;
    private  long commande_id;
    private long produit_id;
}
