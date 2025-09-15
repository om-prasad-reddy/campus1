package com.campus1.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.campus1.manager.model.ManagerNotifications;


@Repository
public interface ManagerNotificationsRepository extends JpaRepository<ManagerNotifications,Long> {
	List<ManagerNotifications> findBySentBy(String email);
	
	@Query("SELECT mn FROM ManagerNotifications mn JOIN mn.managerRecipients mr WHERE mr.manager.id = :managerId")
    List<ManagerNotifications> findReceivedByManagerId(@Param("managerId") Long managerId);
}
