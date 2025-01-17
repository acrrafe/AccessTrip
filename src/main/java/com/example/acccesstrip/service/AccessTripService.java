package com.example.acccesstrip.service;

import com.example.acccesstrip.dto.ItemRequest;
import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Items;

import java.util.List;

public interface AccessTripService {
    Account createAccount(SignUpAccountRequest signUpAccountRequest);
    Account login(LoginAccountRequest loginAccountRequest);
    List<Items> getItems();
    Items getItem(Long itemId);
}
