package com.campus1.manager.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.campus1.manager.dto.ManagerAdminDto;
import com.campus1.manager.dto.ManagerManagerProfileDto;
import com.campus1.manager.exception.ManagerManagerNotFoundException;
import com.campus1.manager.model.ManagerAdmin;
import com.campus1.manager.model.ManagerManager;
import com.campus1.manager.model.ManagerStudents;
import com.campus1.manager.model.ManagerUsers;
import com.campus1.manager.repository.ManagerAdminRepository;
import com.campus1.manager.repository.ManagerManagerRepository;
import com.campus1.manager.repository.ManagerStudentsRepository;
import com.campus1.manager.repository.ManagerUserRepository;

@Service
public class ManagerManagerService {

    private final ManagerManagerRepository managerRepo;
    private final ManagerAdminRepository adminRepo;
    private final ManagerUserRepository userRepo;
    private final ManagerStudentsRepository stuRepo;

    public ManagerManagerService(ManagerManagerRepository managerRepo, ManagerAdminRepository adminRepo, ManagerUserRepository userRepo, ManagerStudentsRepository stuRepo) {
        this.managerRepo = managerRepo;
        this.adminRepo = adminRepo;
        this.userRepo = userRepo;
        this.stuRepo = stuRepo;
    }

    public ManagerManager getManagerByEmail(String email) {
        ManagerManager m = managerRepo.findByEmail(email);
        if (m == null) {
            throw new ManagerManagerNotFoundException("ManagerManager not Found");
        } else {
            return m;
        }
    }

    public List<ManagerAdmin> getAdminsByManagerId(Long managerId) {
        return adminRepo.findByManagerId(managerId);
    }

    public List<ManagerAdminDto> getAdminsWithRecruitCount(Long managerId, LocalDate date) {
        List<ManagerAdmin> managerAdmins = adminRepo.findByManagerId(managerId);

        return managerAdmins.stream()
            .map(admin -> {
                long recruitsCount;
                if (date != null) {
                    recruitsCount = stuRepo.countRecruitsByAdminAndDate(admin.getId(), date);
                } else {
                    recruitsCount = stuRepo.countRecruitsByAdmin(admin.getId());
                }

                return new ManagerAdminDto(
                        admin.getId(),
                        admin.getName(),
                        admin.getEmail(),
                        admin.getPhone(),
                        admin.getDesignation(),
                        admin.getAddress(),
                        recruitsCount
                );
            })
            .toList();
    }

    public ManagerManagerProfileDto getManagerProfileByEmail(String email) {
        ManagerManager managerManager = managerRepo.findByEmail(email);

        ManagerManagerProfileDto dto = new ManagerManagerProfileDto();
        dto.setName(managerManager.getName());
        dto.setEmail(managerManager.getEmail());
        dto.setRole(managerManager.getRole());
        dto.setPhone(managerManager.getPhone());
        dto.setAddress(managerManager.getAddress());

        if (managerManager.getProfilePicture() != null) {
            String base64 = Base64.getEncoder().encodeToString(managerManager.getProfilePicture());
            dto.setImageBase64("data:image/png;base64," + base64);
        }

        return dto;
    }

    public void updateProfileWithImage(ManagerManagerProfileDto dto, MultipartFile file) throws IOException {
        ManagerManager existing = getManagerByEmail(dto.getEmail());

        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());

        if (file != null && !file.isEmpty()) {
            existing.setProfilePicture(file.getBytes());
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            existing.setImageBase64("data:" + file.getContentType() + ";base64," + base64);
        }

        managerRepo.save(existing);
    }

    public void removeProfilePicture(String email) {
        ManagerManager existing = getManagerByEmail(email);
        if (existing != null) {
            existing.setProfilePicture(null);
            managerRepo.save(existing);
        }
    }

    public void save(ManagerManager managerManager) {
        managerRepo.save(managerManager);
    }

    public void assignMultipleTasks(String username, Long adminId, List<Long> userIds) {
        ManagerManager m = getManagerByEmail(username);
        ManagerAdmin managerAdmin = adminRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("ManagerAdmin not found with ID: " + adminId));

        List<ManagerUsers> tasks = userRepo.findAllById(userIds);
        List<ManagerStudents> studentsToSave = new ArrayList<>();
        for (ManagerUsers user : tasks) {
            ManagerStudents st = new ManagerStudents();
            st.setName(user.getName());
            st.setEmail(user.getEmail());
            st.setPhone(user.getPhone());
            st.setQualification(user.getQualification());
            st.setAssignDate(LocalDate.now());
            st.setAdmin(managerAdmin);
            st.setManager(m);
            st.setUser(user);
            studentsToSave.add(st);
            user.setAdmin(managerAdmin);
            userRepo.save(user);
        }

        stuRepo.saveAll(studentsToSave);
    }
}