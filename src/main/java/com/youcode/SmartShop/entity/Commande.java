package com.youcode.SmartShop.entity;

import com.youcode.SmartShop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> aticles ;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiement = new ArrayList<>();
    private LocalDate date = LocalDate.now();
    private BigDecimal sousTotal;
    private int remise = 0;
    private int TVA = 20;
    private BigDecimal total = BigDecimal.ZERO;
    private String codePromo;
    private OrderStatus statut = OrderStatus.PENDING;
    private BigDecimal montant_restant;
}
