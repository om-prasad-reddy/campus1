// Ticket_Repository.java
package com.campus1.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.admin.entity.AdminTicketEntity;

@Repository
public interface AdminTicket_Repository extends JpaRepository<AdminTicketEntity, Long> {
}