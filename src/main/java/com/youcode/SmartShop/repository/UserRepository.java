package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String name);
}
