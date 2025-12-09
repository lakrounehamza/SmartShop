package com.youcode.SmartShop.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProduitUpdateRequestDto(
      @NotBlank(message = "Le nom du produit ne peut pas etre vide.")
      @Size(min = 3, max = 100, message = "Le nom du produit doit etre compris entre 3 et 100 caracteres.")
      String nom,
      @NotNull(message = "Le prix ne peut pas etre nul.")
      @DecimalMin(value = "0.01", inclusive = true, message = "Le prix doit etre superieur a zero.")
      BigDecimal prix,
      @NotNull(message = "Le stock ne peut pas etre nul.")
      @PositiveOrZero(message = "Le stock ne peut pas etre negatif.")
      int stock,
      boolean deleted) {
}
