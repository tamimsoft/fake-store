package com.tamimSoft.fakeStore.controller.user;

import com.tamimSoft.fakeStore.dto.UserDTO;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
@Tag(name = "User APIs")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Get user details", description = "Retrieve user details such as first name, last name, email, and phone number.")
    public ResponseEntity<ApiResponse<User>> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "User details retrieved successfully", user));
    }

    @PatchMapping()
    @Operation(summary = "Update user details", description = "Update user details such as first name, last name, password, email, and phone number.")
    public ResponseEntity<ApiResponse<User>> updateUser(UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "User details updated successfully", updatedUser));
    }

    @DeleteMapping()
    @Operation(summary = "Delete user", description = "Delete user by username.")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
