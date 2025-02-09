package com.tamimSoft.fakeStore.controller.user;

import com.tamimSoft.fakeStore.dto.UserDTO;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.findByUserName(authentication.getName()));
    }

    @PatchMapping()
    @Operation(summary = "Update user details", description = "Update user details such as first name, last name, password, email, and phone number.")
    public ResponseEntity<User> updateUser(UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
