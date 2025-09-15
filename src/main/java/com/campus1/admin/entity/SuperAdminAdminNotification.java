package com.campus1.admin.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_notification")
@Data
public class SuperAdminAdminNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "is_read", columnDefinition = "bit(1) default 0")
    private Boolean isRead; // Use Boolean instead of boolean to handle null values
    
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Profile_Admin admin;
    
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Admin_Main_Notification notification;
    
    @Column(name = "sent_by")
    private String sentBy;
    
    @Column(name = "sent_role")
    private String sentRole;
    
    // Add a convenience method to check if the notification is read
    public boolean isReadStatus() {
        return isRead != null && isRead;
    }
}