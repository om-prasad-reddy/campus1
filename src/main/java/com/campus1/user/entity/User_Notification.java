package com.campus1.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
public class User_Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = true, length = 2000)
    private String message;

    @Column(name = "sent_at", nullable = true)
    private LocalDateTime sentAt;

    @Column(name = "sent_by", nullable = true, length = 255)
    private String sentBy;

    @Column(name = "subject", nullable = true, length = 255)
    private String subject;

    @Column(name = "sent_role", nullable = true, length = 255)
    private String sentRole;

    @Column(name = "priority", nullable = true, length = 255)
    private String priority;
}