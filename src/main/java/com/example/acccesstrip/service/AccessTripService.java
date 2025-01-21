package com.example.acccesstrip.service;

import com.example.acccesstrip.dto.*;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Items;

import java.util.List;

public interface AccessTripService {
    AccountResponse createAccount(SignUpAccountRequest signUpAccountRequest);
    AccountResponse login(LoginAccountRequest loginAccountRequest);
    List<ItemResponse> getItems();
    Items getItem(Long itemId);
    AddToCartResponse addToCart(ItemRequest itemRequest);
    List<CartItemsResponse> getCartItems(Long accountId);
}
