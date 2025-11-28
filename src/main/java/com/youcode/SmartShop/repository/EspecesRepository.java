package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Especes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecesRepository extends JpaRepository<Especes,Long> {
    Page<Especes> findByCommande_Id(Long id, Pageable pageable);
}
