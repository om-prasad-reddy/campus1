package com.campus1.user.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campus1.user.entity.User_Notification;
import com.campus1.user.service.User_NotificationService;

@RestController
@RequestMapping("/api/user/notifications")
public class User_NotificationController {

    @Autowired
    private User_NotificationService user_NotificationService;

    @GetMapping
    public List<User_Notification> getNotifications(@RequestParam Long userId) {
        return user_NotificationService.getNotificationsByUserId(userId);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId, @RequestParam Long userId) {
        user_NotificationService.markAsRead(userId, notificationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@RequestParam Long userId) {
        user_NotificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId, @RequestParam Long userId) {
        user_NotificationService.deleteNotification(userId, notificationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllNotifications(@RequestParam Long userId) {
        user_NotificationService.deleteAllNotifications(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/counts")
    public User_NotificationService.NotificationCounts getNotificationCounts(@RequestParam Long userId) {
        return user_NotificationService.getNotificationCounts(userId);
    }

    @PostMapping
    public ResponseEntity<User_Notification> createNotification(
            @RequestParam Long userId,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam String priority) {
        User_Notification user_Notification = user_NotificationService.createNotification(userId, subject, message, priority);
        return ResponseEntity.ok(user_Notification);
    }
}