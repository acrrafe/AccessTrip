package com.example.acccesstrip.dto;

import java.time.LocalDate;

public class CartItemsResponse {
    private Long itemId;
    private String itemName;
    private String itemImageURL;
    private String itemDescription;
    private Double itemPrice;
    private Long itemStockQuantity;

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
}
