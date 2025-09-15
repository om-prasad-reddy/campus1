package com.campus1.user.entity;




import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_notifications")
@Data
public class User_UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "notification_id", nullable = true)
    private Long notificationId;

    @Column(name = "sent_by", nullable = true, length = 255)
    private String sentBy;

    @Column(name = "sent_role", nullable = true, length = 255)
    private String sentRole;
}