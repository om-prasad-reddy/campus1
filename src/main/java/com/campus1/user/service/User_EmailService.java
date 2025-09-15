package com.campus1.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class User_EmailService {
    private final JavaMailSender mailSender;

    /**
     * Sends an OTP email to the specified recipient
     * @param recipient Email address of the recipient
     * @param otp The OTP code to send
     * @throws MessagingException if there's an error creating or sending the email
     */
    public void sendOtpEmail(String recipient, String otp) throws MessagingException {
        try {
            log.info("Preparing to send OTP email to: {}", recipient);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(recipient);
            helper.setSubject("Your OTP for Registration");
            
            // HTML email content for better formatting
            String htmlContent = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
                    <h2 style="color: #333; text-align: center;">OTP Verification</h2>
                    <p style="font-size: 16px; color: #555;">
                        Dear User,
                    </p>
                    <p style="font-size: 16px; color: #555;">
                        Your One-Time Password (OTP) for registration is:
                    </p>
                    <div style="background-color: #f0f0f0; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; margin: 20px 0; border-radius: 4px;">
                        %s
                    </div>
                    <p style="font-size: 16px; color: #555;">
                        This OTP is valid for 10 minutes. Please do not share it with anyone.
                    </p>
                    <p style="font-size: 16px; color: #555;">
                        If you didn't request this OTP, please ignore this email.
                    </p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                    <p style="font-size: 14px; color: #777; text-align: center;">
                        &copy; 2025 Your Application. All rights reserved.
                    </p>
                </div>
                """.formatted(otp);
            
            helper.setText(htmlContent, true); // true for HTML content
            
            mailSender.send(message);
            
            log.info("OTP email sent successfully to: {}", recipient);
        } catch (MessagingException e) {
            log.error("Error sending OTP email to {}: {}", recipient, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error sending OTP email to {}: {}", recipient, e.getMessage());
            throw new MessagingException("Failed to send email", e);
        }
    }
}
