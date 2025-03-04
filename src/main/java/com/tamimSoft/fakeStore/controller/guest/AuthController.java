package com.tamimSoft.fakeStore.controller.guest;

import com.tamimSoft.fakeStore.dto.SignUpDTO;
import com.tamimSoft.fakeStore.dto.TokenDTO;
import com.tamimSoft.fakeStore.entity.RefreshToken;
import com.tamimSoft.fakeStore.entity.User;
import com.tamimSoft.fakeStore.exception.InvalidOTPException;
import com.tamimSoft.fakeStore.exception.ResourceNotFoundException;
import com.tamimSoft.fakeStore.response.ApiResponse;
import com.tamimSoft.fakeStore.service.OTPService;
import com.tamimSoft.fakeStore.service.RefreshTokenService;
import com.tamimSoft.fakeStore.service.UserDetailsServiceImpl;
import com.tamimSoft.fakeStore.service.UserService;
import com.tamimSoft.fakeStore.utils.Extensions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final OTPService otpService;

    // Temporary storage for pending users
    private final Map<String, SignUpDTO> pendingUsers = new HashMap<>();

    private String getUserDetailsUserName(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        return userDetailsService.loadUserByUsername(userName).getUsername();
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up a new customer", description = "Allows users to sign up for an account.")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpDTO signUpDTO) {
        User existUser = userService.getUserByUserName(signUpDTO.getUserName());
        if (existUser != null) {
            throw new ResourceNotFoundException("User Already Exists!");
        }
        // Generate OTP
        String otp = otpService.generateOTP(signUpDTO.getEmail());
        // Store customer temporarily
        pendingUsers.put(signUpDTO.getEmail(), signUpDTO);

        // Send OTP to the customer's email
        otpService.sendOTP(signUpDTO.getEmail(), otp);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "OTP sent to your email. Please verify to complete signup.", null));
    }

    @GetMapping("/validate-otp")
    @Operation(summary = "Validate OTP", description = "Validates the OTP sent to the customer's email.")
    public ResponseEntity<ApiResponse<Void>> validateOTP(
            @RequestParam String email,
            @RequestParam String otp
    ) {
        SignUpDTO pendingUser = pendingUsers.get(email);
        if (pendingUser == null) {
            throw new ResourceNotFoundException("User not found or OTP expired.");
        }
        if (otpService.validateOTP(email, otp)) {
            // Save the customer to the database
            userService.createUser(pendingUser);
            // Remove from temporary storage
            pendingUsers.remove(email);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Signup completed successfully.", null));
        } else {
            throw new InvalidOTPException("Invalid OTP.");
        }
    }

    @GetMapping("/login")
    @Operation(summary = "User login", description = "Allows users to log in to their account.")
    public ResponseEntity<ApiResponse<TokenDTO>> userLogin(
            @RequestParam String emailOrUserName,
            @RequestParam String password
    ) {
        String userName = emailOrUserName;
        if (Extensions.isEmail(emailOrUserName)) {
            userName = userService.getUserByEmail(emailOrUserName).getUserName();
        }
        String userDetailsUserName = getUserDetailsUserName(userName, password);
        TokenDTO tokenDTO = refreshTokenService.generateNewTokens(userDetailsUserName);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Login Successful", tokenDTO));
    }

    @GetMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Refreshes the access token using the refresh token.")
    public ResponseEntity<ApiResponse<TokenDTO>> refreshAccessToken(@RequestParam String refreshToken) {
        RefreshToken verifiedToken = refreshTokenService.verifyRefreshToken(refreshToken);
        // Rotate refresh token (delete old, create new)
        refreshTokenService.deleteRefreshToken(verifiedToken);
        TokenDTO tokenDTO = refreshTokenService.generateNewTokens(verifiedToken.getUser().getUserName());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Access token refreshed", tokenDTO));
    }
}