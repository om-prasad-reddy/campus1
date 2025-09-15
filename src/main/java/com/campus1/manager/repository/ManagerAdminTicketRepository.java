package com.campus1.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campus1.manager.model.ManagerAdminTicket;

public interface ManagerAdminTicketRepository extends JpaRepository<ManagerAdminTicket,Long> {
	
	@Query("SELECT t FROM ManagerAdminTicket t WHERE t.admin.email = :adminEmail")
    List<ManagerAdminTicket> findByAdminEmail(@Param("adminEmail") String adminEmail);

	// fetch all admin tickets under a specific manager
    List<ManagerAdminTicket> findByAdminManagerEmail(String managerEmail);
}
