package com.example.acccesstrip.repository;

import com.example.acccesstrip.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
}
