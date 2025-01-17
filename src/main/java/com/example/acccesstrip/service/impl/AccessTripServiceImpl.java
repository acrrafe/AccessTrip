package com.example.acccesstrip.service.impl;

import com.example.acccesstrip.dto.ItemRequest;
import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Items;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
import com.example.acccesstrip.exception.InvalidItemException;
import com.example.acccesstrip.repository.AccountRepository;
import com.example.acccesstrip.repository.ItemRepository;
import com.example.acccesstrip.service.AccessTripService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccessTripServiceImpl implements AccessTripService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final ItemRepository itemRepository;


    public AccessTripServiceImpl(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            ItemRepository itemRepository){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
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

    @Override
    public List<Items> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Items getItem(Long itemId) {
        Optional<Items> item = itemRepository.findById(itemId);
        if(item.isPresent()){
            Items itemsObj = new Items();
            itemsObj.setItemId(item.get().getItemId());
            itemsObj.setItemName(item.get().getItemName());
            itemsObj.setItemDescription(item.get().getItemDescription());
            itemsObj.setItemImageURL(item.get().getItemImageURL());
            itemsObj.setItemPrice(item.get().getItemPrice());
            itemsObj.setItemStockQuantity(item.get().getItemStockQuantity());
            itemsObj.setCreatedAt(item.get().getCreatedAt());
            return itemsObj;
        }else {
            throw new InvalidItemException("Invalid Item Id");
        }
    }
}
