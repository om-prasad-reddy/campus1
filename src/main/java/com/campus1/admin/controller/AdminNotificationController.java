package com.campus1.admin.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.campus1.admin.serv.AdminNotificationService;
import com.campus1.admin.serv.AdminNotificationService.NotificationWithReadStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/admin/notifications")
public class AdminNotificationController {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminNotificationController.class);
    
    @Autowired
    private AdminNotificationService adminNotificationService;
    
    @GetMapping("/admin")
    public ResponseEntity<List<NotificationWithReadStatus>> getAdminNotifications(@RequestParam Long adminId) {
        logger.info("Request received for admin notifications with admin ID: {}", adminId);
        List<NotificationWithReadStatus> notifications = adminNotificationService.getAdminNotifications(adminId);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/manager")
    public ResponseEntity<List<NotificationWithReadStatus>> getManagerNotifications(@RequestParam Long adminId) {
        logger.info("Request received for manager notifications with admin ID: {}", adminId);
        List<NotificationWithReadStatus> notifications = adminNotificationService.getManagerNotifications(adminId);
        return ResponseEntity.ok(notifications);
    }
    
    @PutMapping("/mark-read")
    public ResponseEntity<Boolean> markAsRead(@RequestParam Long notificationId,
                                             @RequestParam String role,
                                             @RequestParam Long adminId) {
        logger.info("Request received to mark notification {} as read for {} admin ID: {}", notificationId, role, adminId);
        
        boolean success = adminNotificationService.markAsRead(notificationId, role, adminId);
        
        if (success) {
            logger.info("Notification {} marked as read successfully", notificationId);
            return ResponseEntity.ok(true);
        } else {
            logger.error("Failed to mark notification {} as read", notificationId);
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Boolean> clearNotifications(@RequestParam Long adminId,
                                                     @RequestParam String role) {
        logger.info("Request received to clear {} notifications for admin ID: {}", role, adminId);
        
        boolean success = adminNotificationService.clearNotifications(adminId, role);
        
        if (success) {
            logger.info("Notifications cleared successfully");
            return ResponseEntity.ok(true);
        } else {
            logger.error("Failed to clear notifications");
            return ResponseEntity.badRequest().body(false);
        }
    }
}