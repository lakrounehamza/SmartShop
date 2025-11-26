package com.youcode.SmartShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Product product;
    private int quantite;
    private double prix;
    private BigDecimal totalLigne;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
}
