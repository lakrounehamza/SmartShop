package com.youcode.SmartShop.dtos.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CommandeCreateRequestDto(
        @NotNull(message = "L'ID du client ne peut pas etre nul.")
        @Positive(message = "L'ID du client ne doit pas etre negatif.")
        long client_id,
        String codePromo
) {}
