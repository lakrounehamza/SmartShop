package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
}
