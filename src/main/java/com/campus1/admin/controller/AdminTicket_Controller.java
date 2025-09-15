// Ticket_Controller.java
package com.campus1.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus1.admin.dto.AdminTicketDto;
import com.campus1.admin.serv.AdminTicket_Service;

@RestController
@RequestMapping("/api/admin/tickets")
@CrossOrigin(origins = "*")
public class AdminTicket_Controller {

    private final AdminTicket_Service ticketService;

    public AdminTicket_Controller(AdminTicket_Service ticketService) {
        this.ticketService = ticketService;
    }

    // Create a new ticket
    @PostMapping("/create")
    public ResponseEntity<AdminTicketDto> createAdminTicket(@RequestBody AdminTicketDto ticketDto) {
        AdminTicketDto ticket = ticketService.createAdminTicket(ticketDto);
        return ResponseEntity.ok(ticket);
    }

    // Get all tickets
    @GetMapping("/getAll")
    public ResponseEntity<List<AdminTicketDto>> getAllTickets() {
        List<AdminTicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    // Get ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdminTicketDto> getTicketById(@PathVariable Long id) {
        AdminTicketDto ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    // Update ticket by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminTicketDto> updateTicket(@PathVariable Long id, @RequestBody AdminTicketDto ticketDto) {
        AdminTicketDto updatedTicket = ticketService.updateAdminTicket(id, ticketDto);
        return ResponseEntity.ok(updatedTicket);
    }

    // Delete ticket by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTicketById(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted successfully.");
    }
}