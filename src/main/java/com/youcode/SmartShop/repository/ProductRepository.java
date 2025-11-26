package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
