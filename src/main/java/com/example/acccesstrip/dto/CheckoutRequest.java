package com.example.acccesstrip.dto;

import java.util.List;

public class CheckoutRequest {
    private Long accountId;
    private List<Long> itemId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<Long> getItemId() {
        return itemId;
    }

    public void setItemId(List<Long> itemId) {
        this.itemId = itemId;
    }
}
