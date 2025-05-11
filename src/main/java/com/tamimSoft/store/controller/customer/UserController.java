package com.tamimSoft.store.controller.customer;

import com.tamimSoft.store.dto.UserDTO;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@Tag(name = "Customer APIs")
public class UserController {

    private final UserService userService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping()
    @Operation(summary = "Get customer details", description = "Retrieve customer details such as first name, last name, email, and phone number.")
    public ResponseEntity<ApiResponse<UserDTO>> getUser() {
        UserDTO userDTO = userService.getUserDTO(getAuthenticatedUsername());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Customer details retrieved successfully", userDTO));
    }

    @PatchMapping()
    @Operation(summary = "Update customer details", description = "Update customer details such as first name, last name, password, email, and phone number.")
    public ResponseEntity<ApiResponse<Void>> updateUser(UserDTO userDTO) {
        userService.updateUserProfileInfo(userDTO, getAuthenticatedUsername());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Customer details updated successfully", null));
    }

    @DeleteMapping()
    @Operation(summary = "Delete customer", description = "Delete customer by username.")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUserByUserName(getAuthenticatedUsername());
        return ResponseEntity.noContent().build();
    }
}
