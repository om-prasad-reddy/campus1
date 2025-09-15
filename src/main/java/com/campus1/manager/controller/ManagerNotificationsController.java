package com.campus1.manager.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campus1.manager.dto.ManagerNotificationRequest;
import com.campus1.manager.dto.ManagerNotificationResponseDto;
import com.campus1.manager.service.ManagerNotificationsService;

@Controller
@RequestMapping("/api/manager/notifications")
public class ManagerNotificationsController {
    
    private final ManagerNotificationsService notificationService;

    public ManagerNotificationsController(ManagerNotificationsService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<ManagerNotificationResponseDto> sendNotification(@RequestBody ManagerNotificationRequest request) {
        System.out.println("Controller...");
        ManagerNotificationResponseDto notification = notificationService.sendNotification(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<ManagerNotificationResponseDto>> getAllNotifications(@RequestParam String email) {
        List<ManagerNotificationResponseDto> notifications = notificationService.getAllNotifications(email);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/received")
    public ResponseEntity<List<ManagerNotificationResponseDto>> getReceivedNotifications(@RequestParam String email) {
       List<ManagerNotificationResponseDto> lis = notificationService.getReceivedNotifications(email);
       return ResponseEntity.ok(lis);
    }
    
    @PostMapping("/mark-read")
    public ResponseEntity<Void> markAsRead(@RequestParam Long notificationId,@RequestParam String managerEmail) {
        notificationService.markNotificationAsRead(notificationId, managerEmail);
        return ResponseEntity.ok().build();
    }
}
