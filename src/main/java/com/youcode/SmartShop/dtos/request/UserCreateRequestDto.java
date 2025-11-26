package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.UserRole;
import jakarta.validation.constraints.*;

public record UserCreateRequestDto(

        @NotBlank(message = "Le nom de user ne peut pas etre vide.")
        @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit etre compris entre 3 et 50 caracteres.")
        String username,
        @NotBlank(message = "Le mot de passe ne peut pas etre vide.")
        @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres.")
        @Pattern(regexp = ".*[A-Z].*", message = "Le mot de passe doit contenir au moins une lettre majuscule.")
        @Pattern(regexp = ".*[0-9].*", message = "Le mot de passe doit contenir au moins un chiffre.")
        String password,
        @NotNull(message = "Le role de user ne peut pas etre null.")
        UserRole role
) {}
