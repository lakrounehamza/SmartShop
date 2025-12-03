package com.youcode.SmartShop.dtos.response;

public record LoginResponseDto(
        String message,
        Long userId,
        String username,
        String role
) {}
