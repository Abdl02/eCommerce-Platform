package com.ecommerce_platform.api.controller;

import com.ecommerce_platform.api.dto.request.CartUpdateRequest;
import com.ecommerce_platform.api.dto.response.CartResponse;
import com.ecommerce_platform.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart Management", description = "Endpoints to manage products within a user's cart.")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    @Operation(summary = "Add a product to the cart", description = "Adds a specified product and quantity to the user's cart.")
    public ResponseEntity<CartResponse> addProductToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        CartResponse response = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @Operation(summary = "Update product quantity in the cart", description = "Updates the quantity of a specific product in the user's cart.")
    public ResponseEntity<CartResponse> updateProductQuantity(
            @RequestParam Long userId,
            @Valid @RequestBody CartUpdateRequest request
    ) {
        CartResponse response = cartService.updateProductQuantity(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove a product from the cart", description = "Removes a product from the user's cart.")
    public ResponseEntity<String> removeProductFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok("Product removed from cart successfully");
    }

    @GetMapping("/items")
    @Operation(summary = "Get cart items", description = "Retrieves all products currently in the user's cart.")
    public ResponseEntity<List<CartResponse>> getCartItemsForUser(@RequestParam Long userId) {
        List<CartResponse> responses = cartService.getCartItemsForUser(userId);
        return ResponseEntity.ok(responses);
    }
}