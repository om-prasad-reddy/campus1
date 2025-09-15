package com.campus1.user.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.campus1.user.entity.User;
import com.campus1.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class User_AuthService {
    private final UserRepository userRepository;
    
    // Existing authentication methods remain unchanged
    public User registerUser(Map<String, String> request) {
        String email = request.get("email");
        log.info("Starting registration process for email: {}", email);
        
        // Validate input
        validateRegistrationRequest(request);
        
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            log.warn("Registration failed: Email already exists - {}", email);
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user with null profile fields
        User user = new User();
        user.setFirstName(request.get("firstName"));
        user.setLastName(request.get("lastName"));
        user.setEmail(email);
        user.setCountryCode(request.get("countryCode"));
        user.setMobile(request.get("mobile"));
        user.setPassword(request.get("password"));
        
        // Initialize profile fields as null
        user.setDob(null);
        user.setGender(null);
        user.setAddress(null);
        user.setFatherName(null);
        user.setMotherName(null);
        user.setLinkedinUrl(null);
        user.setGithubUrl(null);
        user.setPortfolioUrl(null);
        user.setObjective(null);
        user.setProfilePhoto(null);
        user.setSignature(null);
        
        // Save user to database
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    public Map<String, Object> loginUser(String email, String password) {
        log.info("Attempting login for email: {}", email);
        
        // Find user by email
        User user = userRepository.findByEmail(email).orElse(null);
        
        Map<String, Object> response = new HashMap<>();
        
        if (user == null) {
            log.warn("Login failed: User not found - {}", email);
            response.put("success", false);
            response.put("message", "User not found with this email");
            return response;
        }
        
        // Compare plain text passwords
        if (!user.getPassword().equals(password)) {
            log.warn("Login failed: Invalid password for user - {}", email);
            response.put("success", false);
            response.put("message", "Invalid password");
            return response;
        }
        
        log.info("Login successful for user: {} (ID: {})", user.getEmail(), user.getId());
        
        // Add user details to response (excluding sensitive info)
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("countryCode", user.getCountryCode());
        userDetails.put("mobile", user.getMobile());
        
        response.put("success", true);
        response.put("message", "Login successful! Welcome back, " + user.getFirstName());
        response.put("user", userDetails);
        
        return response;
    }
    
    private void validateRegistrationRequest(Map<String, String> request) {
        if (!request.containsKey("firstName") || request.get("firstName").trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        
        if (!request.containsKey("lastName") || request.get("lastName").trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        if (!request.containsKey("countryCode") || request.get("countryCode").trim().isEmpty()) {
            throw new IllegalArgumentException("Country code is required");
        }
        
        if (!request.containsKey("mobile") || request.get("mobile").trim().isEmpty()) {
            throw new IllegalArgumentException("Mobile number is required");
        }
        
        String password = request.get("password");
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
    
    // UserService methods integrated into User_AuthService
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}