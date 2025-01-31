package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.dto.UserRoleDTO;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    final
    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<?> getAllUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.findAllByRole(role));
    }

    @PostMapping("/update-user-role")
    public ResponseEntity<?> saveUserRoles(@RequestBody UserRoleDTO userRoleDTO) {
        try {
            User user = userService.findByUserName(userRoleDTO.getUserName());
            if (user != null) {
                user.setRoles(userRoleDTO.getRoles());
                userService.save(user);
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
