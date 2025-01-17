package com.example.acccesstrip.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_image_url")
    private String itemImageURL;
    @Column(name = "item_desc")
    private String itemDescription;
    @Column(name = "item_price")
    private Double itemPrice;
    @Column(name = "item_quantity")
    private Long itemStockQuantity;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDate.now();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageURL() {
        return itemImageURL;
    }

    public void setItemImageURL(String itemImageURL) {
        this.itemImageURL = itemImageURL;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getItemStockQuantity() {
        return itemStockQuantity;
    }

    public void setItemStockQuantity(Long itemStockQuantity) {
        this.itemStockQuantity = itemStockQuantity;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItems cartItems;
}
