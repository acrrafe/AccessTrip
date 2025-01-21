package com.example.acccesstrip.repository;

import com.example.acccesstrip.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
    public Optional<List<CartItems>> findAllByCart_CartId(Long cartId);
}
