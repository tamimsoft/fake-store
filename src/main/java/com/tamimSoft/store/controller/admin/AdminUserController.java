package com.tamimSoft.store.controller.admin;

import com.tamimSoft.store.dto.UserDto;
import com.tamimSoft.store.response.ApiResponse;
import com.tamimSoft.store.service.UserService;
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
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> userDTOPage = userService.getAllUserDTOs(PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Users retrieved successfully", userDTOPage));
    }

    @GetMapping("/all-users-by-role")
    @Operation(summary = "Get all users", description = "Fetches all users by an specific role.")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> allUsersByRole = userService.getAllUserDTOsByRole(role, PageRequest.of(page, size));
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Users retrieved successfully", allUsersByRole));
    }

    @PatchMapping("/update")
    @Operation(summary = "Update a customer with new roles", description = "Update details of an existing customer.")
    public ResponseEntity<ApiResponse<Void>> updateUser(
            @RequestParam String userName,
            @RequestBody UserDto userDTO
    ) {
        userService.updateUserInfoWithRole(userDTO, userName);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "User details updated successfully", null));
    }
}
