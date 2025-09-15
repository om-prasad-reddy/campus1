package com.campus1.admin.serv;

import com.campus1.admin.entity.AdminManagerNotification;
import com.campus1.admin.entity.SuperAdminAdminNotification;
import com.campus1.admin.repo.AdminNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminNotificationService.class);
    
    @Autowired
    private AdminNotificationRepository adminNotificationRepository;
    
    // Inner class to hold notification data with read status
    public static class NotificationWithReadStatus {
        private Long id;
        private String subject;
        private String message;
        private java.time.LocalDateTime sentAt;
        private String sentBy;
        private String sentRole;
        private String priority;
        private Boolean isRead; // Changed to Boolean to ensure proper serialization
        
        public NotificationWithReadStatus(Long id, String subject, String message, 
                                      java.time.LocalDateTime sentAt, String sentBy, 
                                      String sentRole, String priority, Boolean isRead) {
            this.id = id;
            this.subject = subject;
            this.message = message;
            this.sentAt = sentAt;
            this.sentBy = sentBy;
            this.sentRole = sentRole;
            this.priority = priority;
            this.isRead = isRead;
        }
        
        // Getters
        public Long getId() { return id; }
        public String getSubject() { return subject; }
        public String getMessage() { return message; }
        public java.time.LocalDateTime getSentAt() { return sentAt; }
        public String getSentBy() { return sentBy; }
        public String getSentRole() { return sentRole; }
        public String getPriority() { return priority; }
        public Boolean getIsRead() { return isRead; } // Changed getter to match field name
    }
    
    public List<NotificationWithReadStatus> getAdminNotifications(Long adminId) {
        logger.info("Fetching admin notifications for admin ID: {}", adminId);
        
        try {
            List<SuperAdminAdminNotification> notifications = adminNotificationRepository.findSuperAdminNotificationsByAdminId(adminId);
            List<NotificationWithReadStatus> result = new ArrayList<>();
            
            for (SuperAdminAdminNotification notification : notifications) {
                // Debug log to check the isRead value
                logger.debug("SuperAdmin Notification ID: {}, isRead: {}", notification.getId(), notification.getIsRead());
                
                // Convert bit(1) to Boolean explicitly
                Boolean isRead = notification.getIsRead() != null && notification.getIsRead();
                
                result.add(new NotificationWithReadStatus(
                    notification.getId(),
                    notification.getNotification().getSubject(),
                    notification.getNotification().getMessage(),
                    notification.getNotification().getSentAt(),
                    notification.getSentBy(),
                    notification.getSentRole(),
                    notification.getNotification().getPriority(),
                    isRead
                ));
            }
            
            logger.info("Found {} admin notifications for admin ID: {}", result.size(), adminId);
            return result;
        } catch (Exception e) {
            logger.error("Error fetching admin notifications: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    public List<NotificationWithReadStatus> getManagerNotifications(Long adminId) {
        logger.info("Fetching manager notifications for admin ID: {}", adminId);
        
        try {
            List<AdminManagerNotification> notifications = adminNotificationRepository.findManagerNotificationsByAdminId(adminId);
            List<NotificationWithReadStatus> result = new ArrayList<>();
            
            for (AdminManagerNotification notification : notifications) {
                // Debug log to check the isRead value
                logger.debug("Manager Notification ID: {}, isRead: {}", notification.getId(), notification.getIsRead());
                
                // Convert bit(1) to Boolean explicitly
                Boolean isRead = notification.getIsRead() != null && notification.getIsRead();
                
                result.add(new NotificationWithReadStatus(
                    notification.getId(),
                    notification.getNotification().getSubject(),
                    notification.getNotification().getMessage(),
                    notification.getNotification().getSentAt(),
                    notification.getSentBy(),
                    notification.getSentRole(),
                    notification.getNotification().getPriority(),
                    isRead
                ));
            }
            
            logger.info("Found {} manager notifications for admin ID: {}", result.size(), adminId);
            return result;
        } catch (Exception e) {
            logger.error("Error fetching manager notifications: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    @Transactional
    public boolean markAsRead(Long notificationId, String role, Long adminId) {
        logger.info("Marking notification {} as read for {} admin ID: {}", notificationId, role, adminId);
        
        try {
            int updatedRows;
            
            if ("ADMIN".equalsIgnoreCase(role)) {
                updatedRows = adminNotificationRepository.markSuperAdminNotificationAsRead(notificationId, adminId);
            } else {
                updatedRows = adminNotificationRepository.markManagerNotificationAsRead(notificationId, adminId);
            }
            
            if (updatedRows > 0) {
                logger.info("Successfully marked notification {} as read", notificationId);
                return true;
            } else {
                logger.error("Failed to mark notification {} as read. No rows updated.", notificationId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error marking notification as read: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Transactional
    public boolean clearNotifications(Long adminId, String role) {
        logger.info("Clearing all {} notifications for admin ID: {}", role, adminId);
        
        try {
            int deletedRows;
            
            if ("ADMIN".equalsIgnoreCase(role)) {
                deletedRows = adminNotificationRepository.deleteSuperAdminNotificationsByAdminId(adminId);
            } else {
                deletedRows = adminNotificationRepository.deleteManagerNotificationsByAdminId(adminId);
            }
            
            if (deletedRows >= 0) {
                logger.info("Successfully cleared {} notifications for admin ID: {}", deletedRows, adminId);
                return true;
            } else {
                logger.error("Failed to clear notifications for admin ID: {}", adminId);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error clearing notifications: {}", e.getMessage(), e);
            return false;
        }
    }
}