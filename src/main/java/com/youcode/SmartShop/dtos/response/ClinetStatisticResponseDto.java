package com.youcode.SmartShop.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClinetStatisticResponseDto(
        int total, BigDecimal cumule, LocalDate  firstCommandeDate,LocalDate lastCommandeDate
        ) {}
