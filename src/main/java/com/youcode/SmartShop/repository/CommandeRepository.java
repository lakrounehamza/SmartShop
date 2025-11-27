package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    Page<Commande> findByClient_Id(Long id, Pageable pageable);
}
