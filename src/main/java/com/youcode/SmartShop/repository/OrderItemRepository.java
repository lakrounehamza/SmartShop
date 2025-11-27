package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Page<OrderItem> findByCommandeId(long id);
}
