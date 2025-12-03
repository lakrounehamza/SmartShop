package com.youcode.SmartShop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youcode.SmartShop.enums.PaymentStatus;
import com.youcode.SmartShop.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor

@SuperBuilder
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
    @JsonIgnore
    protected Commande commande;
    private PaymentStatus statut  = PaymentStatus.EN_ATTENTE;
}
