package com.youcode.SmartShop.dtos.request;

import com.youcode.SmartShop.enums.PaymentType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ChequeCreateRequestDto(
        @NotBlank(message = "Le numéro de paiement ne peut pas être vide.")
        String numero_paiement,
        @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à zéro.")
        @Positive(message = "Le montant doit être positive")
        BigDecimal montant,
        @NotNull(message = "Le type de paiement ne peut pas être nul.")
        PaymentType type_paiement,
        @NotNull(message = "La date de paiement ne peut pas être nulle.")
        @Past(message = "La date de paiement doit être dans le passé.")
        LocalDate date_paiement,
        @NotNull(message = "La date d'encaissement ne peut pas être nulle.")
        @Future(message = "La date d'encaissement doit être dans le futur.")
        LocalDate date_encaissement,
        @NotBlank(message = "Le numéro de chèque ne peut pas être vide.")
        @Size(min = 8, message = "Le numéro de chèque il  centaint 8 les chifres")
        String nenuro,
        @NotBlank(message = "La banque ne peut pas être vide.")
        String banque,
        @NotNull(message = "La date d'échéance ne peut pas être nulle.")
        @FutureOrPresent(message = "La date d'échéance doit être dans le présent ou dans le futur.")
        LocalDate echance
        , long commande_id) {
}
