package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.PaymentType;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ChequeResponseDto(
        long id,
        String numero_paiement,
        BigDecimal montant,
        PaymentType type_paiement,
        LocalDate date_paiement,
        LocalDate date_encaissement,
        String nenuro,
        String banque,
        LocalDate echance) {
}
