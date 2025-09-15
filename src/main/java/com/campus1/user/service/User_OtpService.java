package com.campus1.user.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus1.user.entity.User_Otp;
import com.campus1.user.repository.User_OtpRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class User_OtpService {
    private final User_OtpRepository user_OtpRepository;
    private final User_EmailService user_EmailService;
    
    @Transactional
    public String generateAndSendOtp(String email) throws Exception {
        // Delete any existing OTP for this email
        user_OtpRepository.deleteByEmail(email);
        
        // Generate new OTP
        String otp = String.format("%06d", new Random().nextInt(1000000));
        
        // Save OTP to database
        User_Otp otpEntity = new User_Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        otpEntity.setVerified(false);
        user_OtpRepository.save(otpEntity);
        
        // Send OTP via email
        user_EmailService.sendOtpEmail(email, otp);
        
        return "OTP sent successfully";
    }
    
    @Transactional
    public boolean verifyOtp(String email, String otp) {
        User_Otp otpEntity = user_OtpRepository.findByEmail(email).orElse(null);
        
        if (otpEntity == null) {
            return false;
        }
        
        // Check if OTP matches and is not expired
        if (otpEntity.getOtp().equals(otp) && 
            otpEntity.getExpiresAt().isAfter(LocalDateTime.now()) && 
            !otpEntity.isVerified()) {
            
            // Mark OTP as verified
            user_OtpRepository.markAsVerified(email);
            return true;
        }
        
        return false;
    }
}
