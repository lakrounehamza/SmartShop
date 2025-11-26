package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.entity.Commande;
import com.youcode.SmartShop.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChequeResponseDto {
    private long id   ;
    private   String numero_paiement;
    private BigDecimal montant ;
    private PaymentType type_paiement ;
    private LocalDate  date_paiement ;
    private LocalDate  date_encaissement ;
    private String nenuro;
    private String banque;
    private LocalDate echance;
}
