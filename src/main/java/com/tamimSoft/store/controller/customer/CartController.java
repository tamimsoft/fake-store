package com.tamimSoft.store.controller.customer;

import com.tamimSoft.store.dto.AddToCartDTO;
import com.tamimSoft.store.dto.CartDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer/cart")
@PreAuthorize("hasRole('CUSTOMER')")
@Tag(name = "Customer APIs")
public class CartController {

    private final CartService cartService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping()
    @Operation(summary = "Get all carts", description = "Get all carts of the authenticated customer.")
    public ResponseEntity<ApiResponse<List<CartDTO>>> getAllCarts() {
        List<CartDTO> cartDTOPage = cartService.getAllCartDTOsByUserName(getAuthenticatedUsername());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Carts retrieved successfully", cartDTOPage));
    }

    @PostMapping()
    @Operation(summary = "Add to cart", description = "Add a product to the cart.")
    public ResponseEntity<ApiResponse<Void>> addToCart(
            @RequestBody AddToCartDTO addToCartDTO
    ) {
        cartService.addToCart(addToCartDTO, getAuthenticatedUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(HttpStatus.CREATED, "Product added to cart successfully", null)
        );
    }


    @PatchMapping()
    @Operation(summary = "Update cart", description = "Update cart details.")
    public ResponseEntity<ApiResponse<Void>> updateCart(
            @RequestParam String cartId,
            @RequestBody AddToCartDTO addToCartDTO
    ) {
        cartService.updateCart(cartId, addToCartDTO);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Cart updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete cart", description = "Delete the authenticated user's cart.")
    public ResponseEntity<Void> deleteCart(@RequestParam String cartId) {
        cartService.deleteCartById(cartId);
        return ResponseEntity.noContent().build();
    }
}
