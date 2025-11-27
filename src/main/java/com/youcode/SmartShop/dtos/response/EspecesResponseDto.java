package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.PaymentType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
public record EspecesResponseDto(
        long id,
        String numero_paiement,
        BigDecimal montant,
        PaymentType type_paiement,
        LocalDate date_paiement,
        LocalDate date_encaissement,
        String Recu) {
}
