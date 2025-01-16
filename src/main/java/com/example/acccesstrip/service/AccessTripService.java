package com.example.acccesstrip.service;

import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;

public interface AccessTripService {
    Account createAccount(SignUpAccountRequest signUpAccountRequest);
    Account login(LoginAccountRequest loginAccountRequest);
}
