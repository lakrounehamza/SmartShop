package com.youcode.SmartShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
            this.code = "PROMO-" + String.format("%04d", (id != null ? id : 0));
        }
    }
}
