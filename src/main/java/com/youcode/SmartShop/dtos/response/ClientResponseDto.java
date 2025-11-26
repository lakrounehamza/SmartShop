package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.CustomerTier;

public record ClientResponseDto(
        Long id,
        String nom,
        String email,
        CustomerTier niveauFidelite,
        Long user_id) {}
