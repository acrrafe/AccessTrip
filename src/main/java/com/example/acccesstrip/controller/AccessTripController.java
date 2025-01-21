package com.example.acccesstrip.controller;

import com.example.acccesstrip.dto.*;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Items;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.BadRequestException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
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

    public AccessTripController(
            AccessTripService accessTripService
            ){
        this.accessTripService = accessTripService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createAccount")
    public ResponseEntity<Object> createAccount
            (@RequestBody  SignUpAccountRequest signUpAccountRequest){
        try{
            AccountResponse account =
                    accessTripService.createAccount(signUpAccountRequest);
            return ResponseEntity.status(HttpStatus.OK).body(account);
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
            AccountResponse account = accessTripService.login(loginAccountRequest);
            return ResponseEntity.status(HttpStatus.OK).body(account);
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
            List<ItemResponse> items = accessTripService.getItems();
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addToCart")
    public ResponseEntity<Object> addToCart(
            @RequestBody ItemRequest itemRequest){
        try{
            AddToCartResponse addToCartResponse = accessTripService.addToCart(itemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(addToCartResponse);
        } catch (Exception e) {
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getItems/{accountId}")
    public ResponseEntity<Object> getCartItems(
            @PathVariable Long accountId){
        try{
            List<CartItemsResponse> cartItemsResponses =
                    accessTripService.getCartItems(accountId);
            return ResponseEntity.status(HttpStatus.OK).body(cartItemsResponses);
        } catch (Exception e) {
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
        }
    }

}
