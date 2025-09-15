package com.campus1.admin.repo;

import com.campus1.admin.entity.AdminManagerNotification;
import com.campus1.admin.entity.SuperAdminAdminNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminNotificationRepository extends JpaRepository<AdminManagerNotification, Long> {
    
    // Manager Notifications
    @Query("SELECT n FROM AdminManagerNotification n WHERE n.admin.id = :adminId ORDER BY n.notification.sentAt DESC")
    List<AdminManagerNotification> findManagerNotificationsByAdminId(@Param("adminId") Long adminId);
    
    @Query("SELECT n FROM AdminManagerNotification n WHERE n.admin.id = :adminId AND n.isRead = false ORDER BY n.notification.sentAt DESC")
    List<AdminManagerNotification> findUnreadManagerNotificationsByAdminId(@Param("adminId") Long adminId);
    
    @Modifying
    @Query("UPDATE AdminManagerNotification n SET n.isRead = true WHERE n.id = :notificationId AND n.admin.id = :adminId")
    int markManagerNotificationAsRead(@Param("notificationId") Long notificationId, @Param("adminId") Long adminId);
    
    @Modifying
    @Query("DELETE FROM AdminManagerNotification n WHERE n.admin.id = :adminId")
    int deleteManagerNotificationsByAdminId(@Param("adminId") Long adminId);
    
    // SuperAdmin Notifications
    @Query("SELECT n FROM SuperAdminAdminNotification n WHERE n.admin.id = :adminId ORDER BY n.notification.sentAt DESC")
    List<SuperAdminAdminNotification> findSuperAdminNotificationsByAdminId(@Param("adminId") Long adminId);
    
    @Query("SELECT n FROM SuperAdminAdminNotification n WHERE n.admin.id = :adminId AND n.isRead = false ORDER BY n.notification.sentAt DESC")
    List<SuperAdminAdminNotification> findUnreadSuperAdminNotificationsByAdminId(@Param("adminId") Long adminId);
    
    @Modifying
    @Query("UPDATE SuperAdminAdminNotification n SET n.isRead = true WHERE n.id = :notificationId AND n.admin.id = :adminId")
    int markSuperAdminNotificationAsRead(@Param("notificationId") Long notificationId, @Param("adminId") Long adminId);
    
    @Modifying
    @Query("DELETE FROM SuperAdminAdminNotification n WHERE n.admin.id = :adminId")
    int deleteSuperAdminNotificationsByAdminId(@Param("adminId") Long adminId);
}