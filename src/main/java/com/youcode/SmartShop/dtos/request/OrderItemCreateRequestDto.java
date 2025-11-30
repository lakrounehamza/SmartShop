package com.youcode.SmartShop.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemCreateRequestDto(

        @NotNull(message = "La quantite ne peut pas etre nulle.")
        @Min(value = 1, message = "La quantite doit etre superieure ou egale à 1.")
        int quantite,
        @NotNull(message = "L'ID du produit ne peut pas etre nul.")
        @Positive(message = "L'ID du produit doit etre un nombre positif.")
        long produit_id
) {}
