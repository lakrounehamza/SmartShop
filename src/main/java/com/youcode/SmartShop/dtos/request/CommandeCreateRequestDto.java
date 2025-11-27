package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.entity.OrderItem;
import com.youcode.SmartShop.entity.Paiement;
import com.youcode.SmartShop.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record CommandeCreateRequestDto(
        @NotNull(message = "L'ID du client ne peut pas etre nul.")
        @Positive(message = "L'ID du client ne doit pas etre negatif.")
        long client_id,
        @NotNull(message = "La commande doit contenir au minimum un article.")
        List<OrderItem> aticles,
        @NotNull(message = "La liste des paiements ne peut pas etre vide.")
        List<Paiement> paiement,
        @NotNull(message = "La date de la commande ne peut pas etre nulle.")
        LocalDate date,
        @DecimalMin(value = "0.0", inclusive = false, message = "Le sous-total doit etre superieur à zero.")
        @Positive(message = "Le sous-total doit etre un montant positif.")
        BigDecimal sousTotal,
        @Positive(message = "La remise doit etre un nombre positif.")
        int remise,
        @Positive(message = "Le taux de TVA doit etre un nombre positif.")
        int TVA,
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant total doit etre superieur à zero.")
        @Positive(message = "Le montant total doit etre positif.")
        BigDecimal total,
        String codePromo,
        @NotNull(message = "Le statut de la commande est obligatoire.")
        OrderStatus statut,
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant restant doit etre superieur e zero.")
        @Positive(message = "Le montant restant doit etre positif.")
        BigDecimal montant_restant
) {}
