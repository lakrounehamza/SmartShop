package com.youcode.SmartShop.entity;

import com.youcode.SmartShop.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id   ;
    private   String numero_paiement;
    private BigDecimal montant ;
    private PaymentType type_paiement ;
    private LocalDate  date_paiement ;
    private LocalDate  date_encaissement ;
    @ManyToOne
    @JoinColumn(name = "commande_id" ,nullable = false)
    private Commande commande;
}
