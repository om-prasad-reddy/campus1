package com.campus1.admin.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
public class Admin_Main_Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 2000)
    private String message;
    
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    
    @Column(name = "sent_by")
    private String sentBy;
    
    @Column(length = 255)
    private String subject;
    
    @Column(name = "sent_role")
    private String sentRole;
    
    @Column(length = 255)
    private String priority;
}