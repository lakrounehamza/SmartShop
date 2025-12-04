package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.CodePromo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodePromoRepository extends JpaRepository<CodePromo,Long> {
    Optional<CodePromo>  findByCode(String code);
}
