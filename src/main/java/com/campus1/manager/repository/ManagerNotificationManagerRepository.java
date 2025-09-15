package com.campus1.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.campus1.manager.model.ManagerNotificationManager;

@Repository
public interface ManagerNotificationManagerRepository extends JpaRepository<ManagerNotificationManager, Long> {

	 @Query("SELECT nm FROM ManagerNotificationManager nm JOIN nm.manager m WHERE nm.notifications.id = :notificationId AND m.email = :email")
	 Optional<ManagerNotificationManager> findByNotificationIdAndManagerEmail(@Param("notificationId") Long notificationId,
	                                                                            @Param("email") String email);
}
