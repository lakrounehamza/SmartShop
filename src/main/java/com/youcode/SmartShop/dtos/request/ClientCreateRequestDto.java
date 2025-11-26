package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.CustomerTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClientCreateRequestDto(
        @NotBlank(message = "Le nom ne peut pas etre vide ou null.")
        String nom,
        @Email(message = "l'email doit entre valide")
        String email,
        CustomerTier niveauFidelite,
        @Positive(message = "l'id de  user  doit  etre un long positive")
        @NotNull(message = "l'Id   de  user  ne pas etre null")
        Long user_id) {
}
