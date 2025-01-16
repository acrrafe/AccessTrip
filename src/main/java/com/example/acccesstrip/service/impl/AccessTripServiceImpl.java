package com.example.acccesstrip.service.impl;

import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
import com.example.acccesstrip.repository.AccountRepository;
import com.example.acccesstrip.service.AccessTripService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccessTripServiceImpl implements AccessTripService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public AccessTripServiceImpl(
            AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Account createAccount(SignUpAccountRequest signUpAccountRequest) {
        Optional<Account> acc = accountRepository.findByAccountEmail(signUpAccountRequest.getAccountEmail());
        if(acc.isPresent()){
            throw new AccountAlreadyExistException("This email is already exist!");
        }
        Account account = new Account();
        account.setAccountEmail(signUpAccountRequest.getAccountEmail());
        account.setAccountName(signUpAccountRequest.getAccountUserName());
        account.setAccountPassword(passwordEncoder.encode(signUpAccountRequest.getAccountPassword()));
        account.setCreatedAt(LocalDate.now());
        return account;
    }

    @Override
    public Account login(LoginAccountRequest loginAccountRequest) {
        Optional<Account> account = accountRepository.findByAccountEmail(loginAccountRequest.getAccountEmail());
        if(account.isEmpty() || !passwordEncoder.matches
                (loginAccountRequest.getAccountPassword(), account.get().getAccountPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }
        Account accountOpt = new Account();
        accountOpt.setAccountName(account.get().getAccountName());
        accountOpt.setAccountEmail(account.get().getAccountEmail());
        return accountOpt;
    }
}
