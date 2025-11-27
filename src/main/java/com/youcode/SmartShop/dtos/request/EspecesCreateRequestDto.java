package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.PaymentType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EspecesCreateRequestDto(
        @NotBlank(message = "Le numero de paiement ne peut pas etre vide.")
        String numero_paiement,
        @NotNull(message = "Le montant ne peut pas etre nul.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit etre superieur a zero.")
        BigDecimal montant,
        @NotNull(message = "Le type de paiement est obligatoire.")
        PaymentType type_paiement,
        @NotNull(message = "La date de paiement ne peut pas etre nulle.")
        @Past(message = "La date de paiement doit etre dans le passe.")
        LocalDate date_paiement,
        @NotNull(message = "La date d'encaissement ne peut pas etre nulle.")
        @Future(message = "La date d'encaissement doit etre dans le futur.")
        LocalDate date_encaissement,
        @NotBlank(message = "Le reçu ne peut pas etre vide.")
        String Recu
) {}
