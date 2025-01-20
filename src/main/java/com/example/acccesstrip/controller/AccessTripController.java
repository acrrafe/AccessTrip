package com.example.acccesstrip.controller;

import com.example.acccesstrip.dto.*;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Items;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.BadRequestException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
import com.example.acccesstrip.repository.AccountRepository;
import com.example.acccesstrip.service.AccessTripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/access-trip")
public class AccessTripController {

    private final AccessTripService accessTripService;
    private final AccountRepository accountRepository;

    public AccessTripController(
            AccessTripService accessTripService,
            AccountRepository accountRepository
            ){
        this.accessTripService = accessTripService;
        this.accountRepository = accountRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createAccount")
    public ResponseEntity<Object> createAccount
            (@RequestBody  SignUpAccountRequest signUpAccountRequest){
        try{
            Account account =
                    accessTripService.createAccount(signUpAccountRequest);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountName(account.getAccountName());
            accountResponse.setAccountEmail(account.getAccountEmail());
            accountResponse.setCreatedAt(account.getCreatedAt());
            accountRepository.save(account);
            return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        }catch (AccountAlreadyExistException e){
            throw new BadRequestException(
                    HttpStatus.CONFLICT.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }catch (InvalidCredentialsException e){
            throw new BadRequestException(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        } catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginAccountRequest loginAccountRequest){
        try{
            Account account = accessTripService.login(loginAccountRequest);
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccountName(account.getAccountName());
            accountResponse.setAccountEmail(account.getAccountEmail());
            accountResponse.setCreatedAt(account.getCreatedAt());
            return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
        }catch (AccountAlreadyExistException | InvalidCredentialsException e){
            throw new BadRequestException(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/home")
    public ResponseEntity<Object> getItems(){
        try{
            List<Items> items = accessTripService.getItems();
            return ResponseEntity.status(HttpStatus.OK).body(items);
        }catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/home/{itemId}")
    public ResponseEntity<Object> getItem(
            @PathVariable Long itemId){
        try{
            Items item = accessTripService.getItem(itemId);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        }catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }
    // TODO: Create POST for add to cart
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/home/addToCart")
    public ResponseEntity<Object> addToCart(
            @RequestBody ItemRequest itemRequest){
        try{
            AddToCartReponse addToCartReponse = accessTripService.addToCart(itemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(addToCartReponse);
        } catch (Exception e) {
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }


    }

}
