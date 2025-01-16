package com.example.acccesstrip.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemID;

    @Column(name = "cart_item_quantity")
    private LocalDate cartItemQuantity;

    @OneToMany(mappedBy = "cartItems")
    private List<Cart> cart;

    @OneToMany(mappedBy = "cartItems")
    private List<Items> items;
}
