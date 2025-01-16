package com.example.acccesstrip.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDate.now();
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @OneToMany(mappedBy = "cart")
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItems cartItems;
}
