package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
