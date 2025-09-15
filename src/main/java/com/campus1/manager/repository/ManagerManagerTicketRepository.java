package com.campus1.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campus1.manager.model.ManagerManagerTicket;

public interface ManagerManagerTicketRepository extends JpaRepository<ManagerManagerTicket,Long> {
	
	 @Query("SELECT t FROM ManagerManagerTicket t WHERE t.manager.email = :managerEmail")
	    List<ManagerManagerTicket> findByManagerEmail(@Param("managerEmail") String managerEmail);

}
