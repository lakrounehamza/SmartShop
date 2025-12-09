package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByDeletedFalse(Pageable pageable);
    Optional<Product> findByIdAndDeletedFalse(Long id);

}
