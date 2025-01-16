package com.example.acccesstrip.service.impl;

import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;

public interface AccessTripService {
     Account createAccount(SignUpAccountRequest signUpAccountRequest);
     Account verifyAccount(LoginAccountRequest loginAccountRequest);
}
