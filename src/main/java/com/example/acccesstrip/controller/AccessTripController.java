package com.example.acccesstrip.controller;

import com.example.acccesstrip.dto.AccountResponse;
import com.example.acccesstrip.dto.LoginAccountRequest;
import com.example.acccesstrip.dto.SignUpAccountRequest;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.ApiErrorResponse;
import com.example.acccesstrip.exception.BadRequestException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
import com.example.acccesstrip.repository.AccountRepository;
import com.example.acccesstrip.service.AccessTripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/access-trip")
public class AccessTripController {

    private final AccessTripService accessTripService;
    private final AccountRepository accountRepository;

    public AccessTripController(
            AccessTripService accessTripService, AccountRepository accountRepository){
        this.accessTripService = accessTripService;
        this.accountRepository = accountRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createAccount")
    public ResponseEntity<Object> createAccount
            (@RequestBody  SignUpAccountRequest signUpAccountRequest){
        try{
            Account account = accessTripService.createAccount(signUpAccountRequest);
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
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(badRequestException);
        }catch (InvalidCredentialsException e){
            throw new BadRequestException(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
        } catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
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
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse);
        }catch (Exception e){
            throw new BadRequestException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
        }
    }
}
