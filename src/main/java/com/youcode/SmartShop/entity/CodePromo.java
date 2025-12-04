package com.youcode.SmartShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private int limit =5;
    private int numberOfTimes=0;
    @PostPersist
    public void generatedCode() {
        if (this.code == null) {
            this.code = "PROMO-" + String.format("%04d", this.id);
        }
    }
}

