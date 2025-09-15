package com.campus1.manager.service;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.campus1.manager.dto.ManagerAdminTicketDto;
import com.campus1.manager.dto.ManagerManagerTicketDto;
import com.campus1.manager.exception.ManagerManagerNotFoundException;
import com.campus1.manager.model.ManagerAdminTicket;
import com.campus1.manager.model.ManagerManager;
import com.campus1.manager.model.ManagerManagerTicket;
import com.campus1.manager.repository.ManagerAdminTicketRepository;
import com.campus1.manager.repository.ManagerManagerRepository;
import com.campus1.manager.repository.ManagerManagerTicketRepository;

@Service
public class ManagerTicketService {
	
	 
	    private final ManagerManagerRepository managerManagerRepository;
	 
	    private final ManagerManagerTicketRepository managerManagerTicketRepository;

	    private final ManagerAdminTicketRepository managerAdminTicketRepository;
	    
	    public ManagerTicketService(ManagerManagerRepository managerManagerRepository,ManagerManagerTicketRepository managerManagerTicketRepository,ManagerAdminTicketRepository managerAdminTicketRepository) {
	    	this.managerManagerRepository=managerManagerRepository;
	    	this.managerManagerTicketRepository=managerManagerTicketRepository;
	    	this.managerAdminTicketRepository=managerAdminTicketRepository;
	    }

	    // ---------------- ManagerManager ----------------
	
	    public ManagerManagerTicketDto createManagerTicket(ManagerManagerTicketDto dto) {
	        ManagerManager managerManager = managerManagerRepository.findByEmail(dto.getManagerEmail());
	        if(managerManager==null) {
	        	throw new ManagerManagerNotFoundException("ManagerManager Not found");
	        }
	                

	        ManagerManagerTicket ticket = new ManagerManagerTicket();
	        ticket.setSubject(dto.getSubject());
	        ticket.setCategory(dto.getCategory());
	        ticket.setPriority(dto.getPriority());
	        ticket.setDescription(dto.getDescription());
	        ticket.setDecision("Pending");
	        ticket.setStatus("Pending");
	        ticket.setCreatedAt(LocalDateTime.now());
	        ticket.setManager(managerManager);

	        ManagerManagerTicket saved = managerManagerTicketRepository.save(ticket);
	        return mapToManagerDto(saved);
	    }


	    public List<ManagerManagerTicketDto> getManagerTickets(String managerEmail) {
	        return managerManagerTicketRepository.findByManagerEmail(managerEmail)
	                .stream().map(this::mapToManagerDto)
	                .collect(Collectors.toList());
	    }

	    
	    public ManagerAdminTicketDto updateAdminTicket(ManagerAdminTicketDto ticketDto) {
	    	// Validate manager
	        ManagerManager managerManager = managerManagerRepository.findByEmail(ticketDto.getManagerEmail());
	        		if(managerManager==null) {
	                	throw new ManagerManagerNotFoundException("ManagerManager not Found");
	                }

	        // Find existing ticket
	        ManagerAdminTicket ticket = managerAdminTicketRepository.findById(ticketDto.getId())
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not Found"));

	        // Ensure ticket belongs to the manager
	     // Ensure ticket belongs to the manager (via ManagerAdmin -> ManagerManager)
	        if (ticket.getAdmin() == null || ticket.getAdmin().getManager() == null ||
	            !ticket.getAdmin().getManager().getEmail().equals(ticketDto.getManagerEmail())) {
	            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized to update this ticket");
	        }

	        // Apply partial updates
	        if(ticketDto.getDecision()!=null) {
	        	if(ticketDto.getDecision().equalsIgnoreCase("Accepted")){
	        		ticket.setStatus("Open");
	        	}
	        	if(ticketDto.getDecision().equalsIgnoreCase("Rejected")){
	        		ticket.setStatus("Rejected");
	        	}
	        	ticket.setDecision(ticketDto.getDecision());
	        }
	        if (ticketDto.getStatus() != null) {
	            ticket.setStatus(ticketDto.getStatus());
	        }
	        
	        ticket.setUpdatedAt(LocalDateTime.now());
	        // Save updated ticket
	        ticket = managerAdminTicketRepository.save(ticket);
	        return mapToAdminDto(ticket);
	    }

	    
	    public List<ManagerAdminTicketDto> getAdminTicketsUnderManager(String managerEmail) {
	        return managerAdminTicketRepository.findByAdminManagerEmail(managerEmail)
	                .stream().map(this::mapToAdminDto)
	                .collect(Collectors.toList());
	    }

	    // ---------------- Mapping ----------------
	    private ManagerManagerTicketDto mapToManagerDto(ManagerManagerTicket ticket) {
	        ManagerManagerTicketDto dto = new ManagerManagerTicketDto();
	        dto.setId(ticket.getId());
	        dto.setSubject(ticket.getSubject());
	        dto.setPriority(ticket.getPriority());
	        dto.setDescription(ticket.getDescription());
	        dto.setStatus(ticket.getStatus());
	        dto.setCreatedAt(ticket.getCreatedAt());
	        dto.setUpdatedAt(ticket.getUpdatedAt());
	        dto.setManagerEmail(ticket.getManager() != null ? ticket.getManager().getEmail() : null);
	        return dto;
	    }

	    private ManagerAdminTicketDto mapToAdminDto(ManagerAdminTicket ticket) {
	        ManagerAdminTicketDto dto = new ManagerAdminTicketDto();
	        dto.setId(ticket.getId());
	        dto.setSubject(ticket.getSubject());
	        dto.setCategory(ticket.getCategory());
	        dto.setPriority(ticket.getPriority());
	        dto.setDescription(ticket.getDescription());
	        dto.setStatus(ticket.getStatus());
	        dto.setDecision(ticket.getDecision());
	        dto.setCreatedAt(ticket.getCreatedAt());
	        dto.setUpdatedAt(ticket.getUpdatedAt());
	        dto.setAdminEmail(ticket.getAdmin() != null ? ticket.getAdmin().getEmail() : null);
	        return dto;
	    }
}
