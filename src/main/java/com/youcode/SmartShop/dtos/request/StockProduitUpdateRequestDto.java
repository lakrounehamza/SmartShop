package com.youcode.SmartShop.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockProduitUpdateRequestDto(
        @NotNull(message = "Le stock ne peut pas etre nul.")
        @PositiveOrZero(message = "Le stock ne peut pas etre negatif.")
        int stock
) {
}
