package com.youcode.SmartShop.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProduitResponseDto(
     Long id,
     String nom,
     BigDecimal prix,
     int stock){}
