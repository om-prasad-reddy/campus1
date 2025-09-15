package com.campus1.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus1.user.entity.User_Notification;
import com.campus1.user.entity.User_UserNotification;
import com.campus1.user.repository.User_NotificationRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class User_NotificationService {

    @Autowired
    private User_NotificationRepository repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<User_Notification> getNotificationsByUserId(Long userId) {
        return repo.findNotificationsByUserId(userId);
    }

    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        User_UserNotification un = entityManager.createQuery(
                "SELECT u FROM User_UserNotification u WHERE u.userId = :userId AND u.notificationId = :notificationId",
                User_UserNotification.class)
            .setParameter("userId", userId)
            .setParameter("notificationId", notificationId)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (un != null) {
            un.setRead(true);
            entityManager.merge(un);
        }
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        repo.markAllAsRead(userId);
    }

    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        repo.deleteByUserIdAndNotificationId(userId, notificationId);
    }

    @Transactional
    public void deleteAllNotifications(Long userId) {
        repo.deleteAllByUserId(userId);
    }

    public NotificationCounts getNotificationCounts(Long userId) {
        long total = repo.countByUserId(userId);
        long unread = repo.countUnreadByUserId(userId);
        NotificationCounts counts = new NotificationCounts();
        counts.setTotal(total);
        counts.setUnread(unread);
        counts.setRead(total - unread);
        return counts;
    }

    @Transactional
    public User_Notification createNotification(Long userId, String subject, String message, String priority) {
        // Save Notification
        User_Notification notification = new User_Notification();
        notification.setSubject(subject);
        notification.setMessage(message);
        notification.setSentBy("Super Admin");
        notification.setSentRole("super-admin");
        notification.setSentAt(LocalDateTime.now());
        notification.setPriority(priority);

        entityManager.persist(notification);

        // Save User Mapping
        User_UserNotification mapping = new User_UserNotification();
        mapping.setRead(false);
        mapping.setUserId(userId);
        mapping.setNotificationId(notification.getId());
        mapping.setSentBy("Super Admin");
        mapping.setSentRole("super-admin");

        entityManager.persist(mapping);

        return notification;
    }

    public static class NotificationCounts {
        private long total;
        private long unread;
        private long read;

        public long getTotal() { return total; }
        public void setTotal(long total) { this.total = total; }
        public long getUnread() { return unread; }
        public void setUnread(long unread) { this.unread = unread; }
        public long getRead() { return read; }
        public void setRead(long read) { this.read = read; }
    }
}
