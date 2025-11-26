package com.youcode.SmartShop.dtos.response;


import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Paiement;
import com.youcode.SmartShop.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CommandeResponseDto {
    private Long id;
    private long client_id;
    private List<OrderItem> aticles;
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
