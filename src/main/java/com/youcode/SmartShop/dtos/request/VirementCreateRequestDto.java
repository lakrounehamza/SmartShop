package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.PaymentType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VirementCreateRequestDto(

        @NotBlank(message = "Le numero de paiement ne peut pas etre vide.")
        String numero_paiement,
        @NotNull(message = "Le montant ne peut pas etre null.")
        @DecimalMin(value = "0.01", inclusive = true, message = "Le montant doit etre superieur a zero.")
        BigDecimal montant,
        @NotNull(message = "Le type de paiement ne peut pas etre null.")
        PaymentType type_paiement,
        @NotNull(message = "La date de paiement ne peut pas etre null.")
        @PastOrPresent(message = "La date de paiement doit etre aujourd'hui ou dans le passe.")
        LocalDate date_paiement,
        @NotNull(message = "La date d'encaissement ne peut pas etre nulle.")
        @FutureOrPresent(message = "La date d'encaissement doit etre aujourd'hui ou dans le futur.")
        LocalDate date_encaissement,
        @NotBlank(message = "La reference ne peut pas etre vide.")
        String reference,
        @NotBlank(message = "Le nom de la banque ne peut pas etre vide.")
        String banque
) {}
