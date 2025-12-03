package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String name);
    Optional<User> findByUsername(String name);
}
