package com.youcode.SmartShop.dtos.response;


import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Paiement;
import com.youcode.SmartShop.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record CommandeResponseDto(
        Long id,
        long client_id,
        List<OrderItem> aticles,
        List<Paiement> paiement,
        LocalDate date,
        BigDecimal sousTotal,
        int remise,
        int TVA,
        BigDecimal total,
        String codePromo,
        OrderStatus statut,
        BigDecimal montant_restant) {}
