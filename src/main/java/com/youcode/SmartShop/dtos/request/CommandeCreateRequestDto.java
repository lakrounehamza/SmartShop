package com.youcode.SmartShop.dtos.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CommandeCreateRequestDto(
        @NotNull(message = "L'ID du client ne peut pas etre nul.")
        @Positive(message = "L'ID du client ne doit pas etre negatif.")
        long client_id,
        @Pattern(
                regexp = "^PROMO-\\d{4}$",
                message = "Le code promo doit respecter le format 'PROMO-XXXX'."
        )
        String codePromo
) {}
