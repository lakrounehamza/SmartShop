package com.youcode.SmartShop.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Cheque extends Paiement {
    private String nenuro;
    private String banque;
    private LocalDate echance;
}
