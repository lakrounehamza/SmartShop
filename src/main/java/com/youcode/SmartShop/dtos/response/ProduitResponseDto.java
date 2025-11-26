package com.youcode.SmartShop.dtos.response;

import java.math.BigDecimal;

public record ProduitResponseDto(
     Long id,
     String nom,
     BigDecimal prix,
     int stock){}
