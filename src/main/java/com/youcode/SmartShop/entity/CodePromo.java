package com.youcode.SmartShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "code_promo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodePromo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(name = "limit_count")
    private int limit = 5;

    private int numberOfTimes = 0;

    @PrePersist
    public void generateCode() {
        if (this.code == null) {
            Random rand  =  new Random();
            int index = rand.nextInt(30);
            String shortUuid = UUID.randomUUID().toString().replace("-", "").substring(index, index+4).toUpperCase();
            this.code = "PROMO-" + shortUuid;
        }
    }
}
