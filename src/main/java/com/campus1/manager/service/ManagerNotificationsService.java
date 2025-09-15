package com.campus1.manager.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.campus1.manager.dto.ManagerNotificationRequest;
import com.campus1.manager.dto.ManagerNotificationResponseDto;
import com.campus1.manager.exception.ManagerManagerNotFoundException;
import com.campus1.manager.model.ManagerAdmin;
import com.campus1.manager.model.ManagerManager;
import com.campus1.manager.model.ManagerNotificationAdmin;
import com.campus1.manager.model.ManagerNotificationManager;
import com.campus1.manager.model.ManagerNotifications;
import com.campus1.manager.repository.ManagerAdminRepository;
import com.campus1.manager.repository.ManagerManagerRepository;
import com.campus1.manager.repository.ManagerNotificationManagerRepository;
import com.campus1.manager.repository.ManagerNotificationsRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class ManagerNotificationsService {
	
	 private static final Logger log = LoggerFactory.getLogger(ManagerNotificationsService.class);
	    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	    private final ManagerNotificationsRepository managerNotificationsRepository;
	    private final ManagerManagerRepository managerManagerRepository;
	    private final ManagerAdminRepository managerAdminRepository;
	    private final ManagerNotificationManagerRepository notMangRepo;

	    @Autowired
	    private JavaMailSender mailSender;

	    public ManagerNotificationsService(ManagerNotificationsRepository managerNotificationsRepository,
	                                       ManagerManagerRepository managerManagerRepository,
	                                       ManagerAdminRepository managerAdminRepository,
	                                       ManagerNotificationManagerRepository notMangRepo) {
	        this.managerNotificationsRepository = managerNotificationsRepository;
	        this.managerManagerRepository = managerManagerRepository;
	        this.managerAdminRepository = managerAdminRepository;
	        this.notMangRepo = notMangRepo;
	    }

	    @Transactional
	    public ManagerNotificationResponseDto sendNotification(ManagerNotificationRequest request) {
	        log.info("Processing notification request for subject: {}", request.getSubject());

	        // Validate sender
	        ManagerManager managerManager = managerManagerRepository.findByEmail(request.getSentBy());
	        if (managerManager == null) {
	            log.error("Sender not found for email: {}", request.getSentBy());
	            throw new ManagerManagerNotFoundException("Sender not found: " + request.getSentBy());
	        }

	        // Create notification entity
	        ManagerNotifications notification = new ManagerNotifications();
	        notification.setSubject(request.getSubject());
	        notification.setMessage(request.getMessage());
	        notification.setSentBy(request.getSentBy());
	        notification.setSentAt(LocalDateTime.now());
	        notification.setSentRole(managerManager.getRole());
	        notification.setPriority(request.getPriority());
	        log.debug("Notification entity created with subject: {}", notification.getSubject());

	        List<ManagerNotificationAdmin> successfulAdmins = new ArrayList<>();

	        // Process admin recipients
	        if (request.getAdminIds() != null && !request.getAdminIds().isEmpty()) {
	            for (Long adminId : request.getAdminIds()) {
	                ManagerAdmin managerAdmin = managerAdminRepository.findById(adminId).orElse(null);
	                if (managerAdmin != null && isValidEmail(managerAdmin.getEmail())) {
	                    boolean emailSent = sendEmailToRecipient(managerAdmin.getEmail(),
	                                                             request.getSubject(),
	                                                             request.getMessage());
	                    if (emailSent) {
	                        ManagerNotificationAdmin na = new ManagerNotificationAdmin();
	                        na.setNotification(notification);
	                        na.setAdmin(managerAdmin);
	                        na.setRead(false);
	                        successfulAdmins.add(na);
	                    }
	                }
	            }
	        }

	        // Prepare response DTO
	        ManagerNotificationResponseDto dto = new ManagerNotificationResponseDto();
	        if (!successfulAdmins.isEmpty()) {
	            notification.getAdminRecipients().addAll(successfulAdmins);
	            managerNotificationsRepository.save(notification);

	            dto.setId(notification.getId());
	            dto.setSubject(notification.getSubject());
	            dto.setMessage(notification.getMessage());
	            dto.setSentBy(notification.getSentBy());
	            dto.setSentAt(notification.getSentAt());
	            dto.setSentRole(notification.getSentRole());
	            dto.setPriority(notification.getPriority());
	        }
	        return dto;
	    }

	    private boolean sendEmailToRecipient(String email, String subject, String message) {
	        try {
	            MimeMessage mimeMessage = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            helper.setFrom("shabbercyetechnology@gmail.com");
	            helper.setTo(email);
	            helper.setSubject(subject);
	            String htmlContent = loadTemplate(subject, message);
	            helper.setText(htmlContent, true);
	            mailSender.send(mimeMessage);
	            return true;
	        } catch (Exception e) {
	            log.error("Failed to send email to {}", email, e);
	            return false;
	        }
	    }

	    private String loadTemplate(String subject, String message) throws IOException {
	        ClassPathResource resource = new ClassPathResource("email-template.html");
	        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	        return template.replace("{{subject}}", subject)
	                       .replace("{{message}}", message.replace("\n", "<br>"));
	    }

	    private boolean isValidEmail(String email) {
	        return email != null && EMAIL_PATTERN.matcher(email).matches();
	    }

	    public List<ManagerNotificationResponseDto> getAllNotifications(String email) {
	        List<ManagerNotifications> managerNotifications = managerNotificationsRepository.findBySentBy(email);

	        List<ManagerNotificationResponseDto> list = new ArrayList<>();
	        for (ManagerNotifications n : managerNotifications) {
	            ManagerNotificationResponseDto dto = new ManagerNotificationResponseDto();
	            dto.setId(n.getId());
	            dto.setSubject(n.getSubject());
	            dto.setMessage(n.getMessage());
	            dto.setSentBy(n.getSentBy());
	            dto.setSentAt(n.getSentAt());
	            dto.setSentRole(n.getSentRole());
	            dto.setPriority(n.getPriority());
	            list.add(dto);
	        }
	        return list;
	    }

	    public List<ManagerNotificationResponseDto> getReceivedNotifications(String email) {
	        ManagerManager managerManager = managerManagerRepository.findByEmail(email);
	        if (managerManager == null) {
	            throw new ManagerManagerNotFoundException("ManagerManager not found with email: " + email);
	        }

	        List<ManagerNotifications> managerNotifications =
	                managerNotificationsRepository.findReceivedByManagerId(managerManager.getId());

	        return managerNotifications.stream().map(n -> {
	            ManagerNotificationManager nm = n.getManagerRecipients().stream()
	                    .filter(r -> r.getManager().getId().equals(managerManager.getId()))
	                    .findFirst()
	                    .orElse(null);

	            return new ManagerNotificationResponseDto(
	                    n.getId(),
	                    n.getSubject(),
	                    n.getMessage(),
	                    n.getSentBy(),
	                    n.getSentAt(),
	                    n.getSentRole(),
	                    nm != null && nm.isRead(),
	                    n.getPriority()
	            );
	        }).toList();
	    }

	    public void markNotificationAsRead(Long notificationId, String managerEmail) {
	        ManagerNotificationManager nm = notMangRepo
	                .findByNotificationIdAndManagerEmail(notificationId, managerEmail)
	                .orElseThrow(() -> new RuntimeException("Notification not found for manager"));

	        nm.setRead(true);
	        notMangRepo.save(nm);
	     }
}