package com.youcode.SmartShop.dtos.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrderItemCreateRequestDto(

        @NotNull(message = "La quantite ne peut pas etre nulle.")
        @Min(value = 1, message = "La quantite doit etre superieure ou egale à 1.")
        int quantite,
        @NotNull(message = "Le prix ne peut pas etre nul.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit etre superieur a zero.")
        double prix,
        @NotNull(message = "L'ID de la commande ne peut pas etre nul.")
        @Positive(message = "L'ID de la commande doit etre un nombre positif.")
        long commande_id,
        @NotNull(message = "L'ID du produit ne peut pas etre nul.")
        @Positive(message = "L'ID du produit doit etre un nombre positif.")
        long produit_id
) {}
