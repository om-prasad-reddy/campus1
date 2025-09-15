package com.campus1.manager.service;

import org.springframework.stereotype.Service;

import com.campus1.manager.dto.ManagerAdminCreateDto;
import com.campus1.manager.model.ManagerAdmin;
import com.campus1.manager.model.ManagerManager;
import com.campus1.manager.repository.ManagerAdminRepository;

@Service
public class ManagerAdminService {

    private final ManagerAdminRepository managerAdminRepository;

    public ManagerAdminService(ManagerAdminRepository managerAdminRepository) {
        this.managerAdminRepository = managerAdminRepository;
    }

    public void createAdminUnderManagerEmail(ManagerAdminCreateDto dto, ManagerManager m) {
        ManagerAdmin managerAdmin = new ManagerAdmin();
        managerAdmin.setName(dto.getName());
        managerAdmin.setEmail(dto.getEmail());
        managerAdmin.setPhone(dto.getPhone()); // Changed to String, removed Long.valueOf
        managerAdmin.setPwd(dto.getPassword());
        managerAdmin.setDesignation(dto.getDesignation());
        managerAdmin.setAddress(dto.getAddress());
        managerAdmin.setManager(m);
        managerAdminRepository.save(managerAdmin);
    }
}