package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Paiement;
import com.youcode.SmartShop.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record CommandeCreateRequestDto(
        @NotNull(message = "L'ID du client ne peut pas etre nul.")
        @Positive(message = "L'ID du client ne doit pas etre negatif.")
        long client_id,
        @NotNull(message = "La commande doit contenir au minimum un article.")
        List<OrderItem> aticles,
        String codePromo
) {}
