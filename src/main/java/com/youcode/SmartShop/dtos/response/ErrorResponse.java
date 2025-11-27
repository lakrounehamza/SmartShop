package com.youcode.SmartShop.dtos.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String path
) {}
