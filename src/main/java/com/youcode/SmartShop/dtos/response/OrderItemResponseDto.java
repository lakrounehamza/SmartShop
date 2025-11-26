package com.youcode.SmartShop.dtos.response;



import java.math.BigDecimal;

 public record OrderItemResponseDto (
     int quantite,
     double prix,
     BigDecimal totalLigne,
      long commande_id,
     long produit_id){}
