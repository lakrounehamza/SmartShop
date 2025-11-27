package com.youcode.SmartShop.entity;

import com.youcode.SmartShop.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    protected long id   ;
    protected   String numero_paiement;
    protected BigDecimal montant ;
    protected PaymentType type_paiement ;
    protected LocalDate  date_paiement ;
    protected LocalDate  date_encaissement ;
    @ManyToOne
    @JoinColumn(name = "commande_id" ,nullable = false)
    protected Commande commande;
}
