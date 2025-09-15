// Ticket_Service.java
package com.campus1.admin.serv;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campus1.admin.dto.AdminTicketDto;
import com.campus1.admin.entity.Profile_Admin;
import com.campus1.admin.entity.AdminTicketEntity;
import com.campus1.admin.repo.AdminRepository;
import com.campus1.admin.repo.AdminTicket_Repository;

@Service
public class AdminTicket_Service {

    private final AdminTicket_Repository ticketRepository;
    private final AdminRepository adminRepository;

    public AdminTicket_Service(AdminTicket_Repository ticketRepository, AdminRepository adminRepository) {
        this.ticketRepository = ticketRepository;
        this.adminRepository = adminRepository;
    }

    public AdminTicketDto createAdminTicket(AdminTicketDto dto) {
        // Find admin by email
        Profile_Admin profile_Admin = adminRepository.findByEmail(dto.getAdminEmail());
        if (profile_Admin == null) {
            throw new RuntimeException("Admin not found with email: " + dto.getAdminEmail());
        }

        // Map DTO -> Entity
        AdminTicketEntity entity = AdminTicketEntity.builder()
                .subject(dto.getSubject())
                .category(dto.getCategory())
                .priority(dto.getPriority())
                .description(dto.getDescription())
                .status("Pending")
                .createdAt(LocalDateTime.now())
                .profile_Admin(profile_Admin)
                .build();

        AdminTicketEntity saved = ticketRepository.save(entity);
 
        // Map Entity -> DTO
        return mapToDto(saved);
    }

    public List<AdminTicketDto> getAllTickets() {
        return ticketRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public AdminTicketDto getTicketById(Long id) {
        AdminTicketEntity entity = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return mapToDto(entity);
    }

    public AdminTicketDto updateAdminTicket(Long id, AdminTicketDto dto) {
        AdminTicketEntity entity = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        if (dto.getSubject() != null) entity.setSubject(dto.getSubject());
        if (dto.getCategory() != null) entity.setCategory(dto.getCategory());
        if (dto.getPriority() != null) entity.setPriority(dto.getPriority());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());

        AdminTicketEntity updated = ticketRepository.save(entity);
        return mapToDto(updated);
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    // Mapper helper
    private AdminTicketDto mapToDto(AdminTicketEntity entity) {
        return new AdminTicketDto(
                entity.getId(),
                entity.getSubject(),
                entity.getCategory(),
                entity.getPriority(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getProfile_Admin() != null ? entity.getProfile_Admin().getEmail() : null
        );
    }
}