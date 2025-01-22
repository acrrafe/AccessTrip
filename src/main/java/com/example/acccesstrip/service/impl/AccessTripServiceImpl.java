package com.example.acccesstrip.service.impl;

import com.example.acccesstrip.dto.*;
import com.example.acccesstrip.entity.Account;
import com.example.acccesstrip.entity.Cart;
import com.example.acccesstrip.entity.CartItems;
import com.example.acccesstrip.entity.Items;
import com.example.acccesstrip.exception.AccountAlreadyExistException;
import com.example.acccesstrip.exception.InvalidCredentialsException;
import com.example.acccesstrip.exception.InvalidItemException;
import com.example.acccesstrip.repository.AccountRepository;
import com.example.acccesstrip.repository.CartItemRepository;
import com.example.acccesstrip.repository.CartRepository;
import com.example.acccesstrip.repository.ItemRepository;
import com.example.acccesstrip.service.AccessTripService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessTripServiceImpl implements AccessTripService {

    private static final Logger logger =
            LoggerFactory.getLogger(AccessTripServiceImpl.class);

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    public AccessTripServiceImpl(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            ItemRepository itemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    @Transactional
    @Override
    public AccountResponse createAccount(SignUpAccountRequest signUpAccountRequest) {
        Optional<Account> acc = accountRepository.findByAccountEmail(signUpAccountRequest.getAccountEmail());
        if(acc.isPresent()){
            throw new AccountAlreadyExistException("This email is already exist!");
        }
        Account account = new Account();
        account.setAccountEmail(signUpAccountRequest.getAccountEmail());
        account.setAccountName(signUpAccountRequest.getAccountUserName());
        account.setAccountPassword(passwordEncoder.encode(signUpAccountRequest.getAccountPassword()));
        account.setCreatedAt(LocalDate.now());
        // Upon creating new account, ensure to assign cart id to it
        Cart cart = new Cart();
        cart.setCreatedAt(LocalDate.now());
        cartRepository.save(cart);
        account.setCart(cart);
        accountRepository.save(account);
        // Expose DTO
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountName(account.getAccountName());
        accountResponse.setAccountEmail(account.getAccountEmail());
        accountResponse.setCreatedAt(account.getCreatedAt());
        return accountResponse;
    }

    @Override
    public AccountResponse login(LoginAccountRequest loginAccountRequest) {
        Optional<Account> account = accountRepository.findByAccountEmail(loginAccountRequest.getAccountEmail());
        if(account.isEmpty() || !passwordEncoder.matches
                (loginAccountRequest.getAccountPassword(), account.get().getAccountPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }
        // DTO
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountId(account.get().getId());
        accountResponse.setAccountName(account.get().getAccountName());
        accountResponse.setAccountEmail(account.get().getAccountEmail());
        accountResponse.setCreatedAt(account.get().getCreatedAt());
        return accountResponse;
    }

    @Override
    public List<ItemResponse> getItems() {
        List<Items> items = itemRepository.findAll();
        return items.stream()
                .map(item -> new ItemResponse(
                        item.getItemId(),
                        item.getItemName(),
                        item.getItemImageURL(),
                        item.getItemDescription(),
                        item.getItemPrice(),
                        item.getItemStockQuantity(),
                        item.getCreatedAt()))
                .collect(Collectors.toList());
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

    @Transactional
    @Override
    public AddToCartResponse addToCart(ItemRequest itemRequest) {
        // If there's no existing cart, create a new one
        Cart cart = cartRepository.findById(itemRequest.getCartId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCartId(itemRequest.getCartId());
                    return newCart;
        });
        // Get item details through item request id
        Items item = itemRepository.findById(itemRequest.getCartItemId())
                .orElseThrow(() -> new InvalidItemException("Item not found"));

        // This will check if the current item request is already in user's cart
        CartItems cartItems = cart.getCartItems().stream()
                .filter(ci -> ci.getItem().getItemId().equals(item.getItemId()))
                .findFirst()
                .orElse(new CartItems());

        cartItems.setCart(cart);
        cartItems.setItem(item);
        cartItems.setCartItemQuantity(cartItems.getCartItemQuantity()
                != null ? cartItems.getCartItemQuantity() + 1 : 1L);
        cart.getCartItems().add(cartItems);
        cartRepository.save(cart);

        AddToCartResponse addToCartResponse = new AddToCartResponse();
        addToCartResponse.setCartId(itemRequest.getCartId());
        addToCartResponse.setItemId(itemRequest.getCartItemId());
        return addToCartResponse;
    }

    @Transactional
    @Override
    public List<CartItemsResponse> getCartItems(Long accountId) {

        Cart cart = cartRepository.findById(accountId)
                .orElseThrow(() -> new InvalidItemException("Invalid ID"));

        List<CartItems> cartItems = cartItemRepository.findAllByCart_CartId(cart.getCartId())
                .orElseThrow(() -> new InvalidItemException("Empty Cart"));
        List<CartItemsResponse> cartItemsResponses = new ArrayList<>();

        cartItems.forEach(cartItem -> {
            CartItemsResponse cartItemsResponse = new CartItemsResponse();
            cartItemsResponse.setItemId(cartItem.getItem().getItemId());
            cartItemsResponse.setItemName(cartItem.getItem().getItemName());
            cartItemsResponse.setItemDescription(cartItem.getItem().getItemDescription());
            cartItemsResponse.setItemPrice(cartItem.getItem().getItemPrice());
            cartItemsResponse.setItemImageURL(cartItem.getItem().getItemImageURL());
            cartItemsResponse.setItemStockQuantity(cartItem.getCartItemQuantity());
            cartItemsResponses.add(cartItemsResponse);
        });

        return cartItemsResponses;
    }

    @Transactional
    @Override
    public CheckoutResponse checkoutItems(CheckoutRequest checkoutRequest) {

        Cart cart = cartRepository.findById(checkoutRequest.getAccountId())
                .orElseThrow(() -> new InvalidItemException("Invalid ID"));

        List<CartItems> cartItems = cartItemRepository.findAllByCart_CartId(cart.getCartId())
                .orElseThrow(() -> new InvalidItemException("Empty Cart"));

        List<CartItems> itemsToRemove = cartItems.stream()
                .filter(cartItem ->
                        checkoutRequest.getItemId().contains(cartItem.getItem().getItemId()))
                .toList();
        if(itemsToRemove.isEmpty()){
            throw new InvalidCredentialsException("Cart is empty!");
        }
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setCartId(cart.getCartId());
        checkoutResponse.setCheckoutItemsCount(itemsToRemove.size());
        cartItemRepository.deleteAll(itemsToRemove);

        return checkoutResponse;
    }
}
