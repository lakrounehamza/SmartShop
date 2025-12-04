package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Page<OrderItem> findByCommandeId(long id, Pageable pageable);
    List<OrderItem> findByProduct_Id(long id);
}
