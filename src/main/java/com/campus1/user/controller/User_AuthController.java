package com.campus1.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus1.user.entity.User;
import com.campus1.user.service.User_AuthService;
import com.campus1.user.service.User_OtpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
@Slf4j
public class User_AuthController {

    private final User_OtpService user_OtpService;
    private final User_AuthService user_AuthService;

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createResponse("Email is required", false));
            }
            
            log.info("Sending OTP to email: {}", email);
            String result = user_OtpService.generateAndSendOtp(email);
            log.info("OTP sent successfully to: {}", email);
            return ResponseEntity.ok(createResponse(result, true));
        } catch (Exception e) {
            log.error("Error sending OTP: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String otp = request.get("otp");
            
            if (email == null || otp == null) {
                return ResponseEntity.badRequest().body(createResponse("Email and OTP are required", false));
            }
            
            log.info("Verifying OTP for email: {}", email);
            boolean isValid = user_OtpService.verifyOtp(email, otp);
            
            if (isValid) {
                log.info("OTP verified successfully for: {}", email);
                return ResponseEntity.ok(createResponse("OTP verified successfully", true));
            } else {
                log.warn("Invalid OTP for email: {}", email);
                return ResponseEntity.badRequest().body(createResponse("Invalid or expired OTP", false));
            }
        } catch (Exception e) {
            log.error("Error verifying OTP: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            log.info("Registering new user with email: {}", email);
            
            // Register the user with null profile fields
            User user = user_AuthService.registerUser(request);
            log.info("User registered successfully with ID: {}", user.getId());
            
            Map<String, Object> response = createResponse("Registration successful! Welcome, " + user.getFirstName(), true);
            response.put("userId", user.getId());
            
            log.info("Registration completed successfully for user: {}", user.getId());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            log.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createResponse(e.getMessage(), false));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            
            log.info("Login attempt for email: {}", email);
            
            // Validate input
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createResponse("Email is required", false));
            }
            
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createResponse("Password is required", false));
            }
            
            Map<String, Object> response = user_AuthService.loginUser(email, password);
            
            if ((boolean) response.get("success")) {
                log.info("Login successful for user: {}", email);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Login failed for user: {}", email);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createResponse("An error occurred during login", false));
        }
    }

    private Map<String, Object> createResponse(String message, boolean success) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("success", success);
        return response;
    }
}