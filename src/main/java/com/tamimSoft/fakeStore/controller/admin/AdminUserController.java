package com.tamimSoft.fakeStore.controller.admin;

import com.tamimSoft.fakeStore.dto.UserDTO;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/user")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin APIs", description = "Admin-only operations")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/all-users")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<User> users = userService.findAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Users retrieved successfully", users));
    }

    @GetMapping("/all-users-by-role")
    @Operation(summary = "Get all users", description = "Fetches all users by an specific role.")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<User> allUsersByRole = userService.findAllUsersByRole(role, PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Users retrieved successfully", allUsersByRole));
    }

    @PatchMapping("/update")
    @Operation(summary = "Update a user with new roles", description = "Update details of an existing user.")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @RequestParam String userName,
            @RequestBody UserDTO userDTO
    ) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with user name: " + userName);
        }
        user.setFirstName(userDTO.getFirstName() == null ? user.getFirstName() : userDTO.getFirstName());
        user.setLastName(userDTO.getLastName() == null ? user.getLastName() : userDTO.getLastName());
        user.setPassword(userDTO.getPassword() == null ? user.getPassword() : userDTO.getPassword());
        user.setEmail(userDTO.getEmail() == null ? user.getEmail() : userDTO.getEmail());
        user.setPhone(userDTO.getPhone() == null ? user.getPhone() : userDTO.getPhone());
        user.getRoles().addAll(userDTO.getRoles() == null ? user.getRoles() : userDTO.getRoles());
        userService.updateUser(user);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "User details updated successfully", user));
    }
}
