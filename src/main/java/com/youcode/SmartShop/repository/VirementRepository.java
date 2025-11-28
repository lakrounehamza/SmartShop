package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Virement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirementRepository extends JpaRepository<Virement,Long> {
    Page<Virement> findByCommande_Id(Long id , Pageable pageable);
}
