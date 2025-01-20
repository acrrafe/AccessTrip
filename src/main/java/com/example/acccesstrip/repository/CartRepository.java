package com.example.acccesstrip.repository;

import com.example.acccesstrip.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
