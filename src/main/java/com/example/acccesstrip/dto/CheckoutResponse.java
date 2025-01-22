package com.example.acccesstrip.dto;

public class CheckoutResponse {
    private Long cartId;
    private int checkoutItemsCount;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public int getCheckoutItemsCount() {
        return checkoutItemsCount;
    }

    public void setCheckoutItemsCount(int checkoutItemsCount) {
        this.checkoutItemsCount = checkoutItemsCount;
    }
}
