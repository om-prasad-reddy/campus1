package com.campus1.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.campus1.user.entity.User_Notification;
import com.campus1.user.entity.User_UserNotification;

@Repository
public interface User_NotificationRepository extends JpaRepository<User_UserNotification, Long> {

    // Fetch all notifications for a user
    @Query("SELECT n FROM User_Notification n JOIN User_UserNotification un ON n.id = un.notificationId WHERE un.userId = :userId")
    List<User_Notification> findNotificationsByUserId(Long userId);

    // Count all notifications
    @Query("SELECT COUNT(un) FROM User_UserNotification un WHERE un.userId = :userId")
    long countByUserId(Long userId);

    // Count unread notifications
    @Query("SELECT COUNT(un) FROM User_UserNotification un WHERE un.userId = :userId AND un.isRead = false")
    long countUnreadByUserId(Long userId);

    // Mark all as read
    @Modifying
    @Query("UPDATE User_UserNotification un SET un.isRead = true WHERE un.userId = :userId")
    void markAllAsRead(Long userId);

    // Delete all for a user
    @Modifying
    @Query("DELETE FROM User_UserNotification un WHERE un.userId = :userId")
    void deleteAllByUserId(Long userId);

    // Delete one notification for a user
    @Modifying
    @Query("DELETE FROM User_UserNotification un WHERE un.userId = :userId AND un.notificationId = :notificationId")
    void deleteByUserIdAndNotificationId(Long userId, Long notificationId);
}
