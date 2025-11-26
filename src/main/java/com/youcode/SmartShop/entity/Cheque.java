package com.youcode.SmartShop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cheque   extends  Paiement{
    private String nenuro  ;
private String banque   ;
private LocalDate echance  ;
}
