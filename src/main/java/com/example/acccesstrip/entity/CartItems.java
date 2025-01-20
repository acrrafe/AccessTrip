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
    private Long cartItemQuantity;

    @OneToMany(mappedBy = "cartItems")
    private List<Cart> cart;

    @OneToMany(mappedBy = "cartItems")
    private List<Items> items;

    public Long getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(Long cartItemID) {
        this.cartItemID = cartItemID;
    }

    public Long getCartItemQuantity() {
        return cartItemQuantity;
    }

    public void setCartItemQuantity(Long cartItemQuantity) {
        this.cartItemQuantity = cartItemQuantity;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
