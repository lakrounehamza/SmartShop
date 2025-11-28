package com.youcode.SmartShop.entity;

import com.youcode.SmartShop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> aticles;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiement;
    private LocalDate date;
    private BigDecimal sousTotal;
    private int remise;
    private int TVA;
    private BigDecimal total;
    private String codePromo;
    private OrderStatus statut;
    private BigDecimal montant_restant;
}
