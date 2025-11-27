package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.CustomerTier;
import jakarta.validation.constraints.NotNull;

public record NiveauFideliteUpdateDto(
        @NotNull(message = "Le niveau de fidélité ne doit pas être vide")
        CustomerTier niveauFidelite
) {}
