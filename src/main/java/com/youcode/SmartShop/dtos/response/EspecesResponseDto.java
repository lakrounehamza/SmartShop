package com.youcode.SmartShop.dtos.response;

import com.youcode.SmartShop.enums.PaymentType;
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
public class EspecesResponseDto {
    private long id   ;
    private   String numero_paiement;
    private BigDecimal montant ;
    private PaymentType type_paiement ;
    private LocalDate date_paiement ;
    private LocalDate  date_encaissement ;
    private String Recu   ;

}
