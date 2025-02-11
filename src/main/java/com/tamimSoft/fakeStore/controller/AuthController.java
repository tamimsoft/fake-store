package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.dto.LoginDTO;
import com.tamimSoft.fakeStore.dto.SignUpDTO;
import com.tamimSoft.fakeStore.entity.RefreshToken;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.InvalidOTPException;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.OTPService;
import com.tamimSoft.fakeStore.service.RefreshTokenService;
import com.tamimSoft.fakeStore.service.UserDetailsServiceImpl;
import com.tamimSoft.fakeStore.service.UserService;
import com.tamimSoft.fakeStore.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final OTPService otpService;

    // Temporary storage for pending users
    private final Map<String, User> pendingUsers = new HashMap<>();

    @PostMapping("/signup")
    @Operation(summary = "Sign up a new user", description = "Allows users to sign up for an account.")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpDTO userDTO) {
        User existUser = userService.findByUserName(userDTO.getUserName());
        if (existUser != null) {
            throw new ResourceNotFoundException("User Already Exists!");
        }

        // Generate OTP
        String otp = otpService.generateOTP();

        // Save user details temporarily
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setRoles(Set.of("USER"));

        // Store user temporarily
        pendingUsers.put(userDTO.getEmail(), user);

        // Send OTP to the user's email
        otpService.sendOTP(userDTO.getEmail(), otp);

        return ResponseEntity.ok("OTP sent to your email. Please verify to complete signup.");
    }

    @PostMapping("/validate-otp")
    @Operation(summary = "Validate OTP", description = "Validates the OTP sent to the user's email.")
    public ResponseEntity<?> validateOTP(@RequestParam String email, @RequestParam String otp) {
        User pendingUser = pendingUsers.get(email);
        if (pendingUser == null) {
            throw new ResourceNotFoundException("User not found or OTP expired.");
        }

        if (otpService.validateOTP(email, otp)) {
            // Save the user to the database
            User savedUser = userService.saveNewUser(pendingUser);
            // Remove from temporary storage
            pendingUsers.remove(email);
            return ResponseEntity.ok(savedUser);
        } else {
            throw new InvalidOTPException("Invalid OTP.");
        }
    }


    @PostMapping("/login")
    @Operation(summary = "User login", description = "Allows users to log in to their account.")
    public ResponseEntity<ApiResponse<Map<String, String>>> userLogin(@RequestBody LoginDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserName());

        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken.getToken());

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Login Successful", tokens));

    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Refreshes the access token using the refresh token.")
    public ResponseEntity<ApiResponse<Map<String, String>>> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        RefreshToken verifiedToken = refreshTokenService.verifyRefreshToken(refreshToken);

        // Generate new access token
        String newAccessToken = jwtUtil.generateToken(verifiedToken.getUser().getUserName());

        // Rotate refresh token (delete old, create new)
        refreshTokenService.deleteRefreshToken(verifiedToken);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(verifiedToken.getUser().getUserName());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken.getToken());

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Access token refreshed", tokens));
    }
}