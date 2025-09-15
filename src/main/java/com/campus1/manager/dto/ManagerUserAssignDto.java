package com.campus1.manager.dto;

import java.time.LocalDate;

import com.campus1.manager.model.ManagerStudents;
import com.campus1.manager.model.ManagerUsers;

public class ManagerUserAssignDto {
	private long id;
	private String name;
	private String qualification;
	private String email;
	private String admin;
	private LocalDate assignedDate;

	public ManagerUserAssignDto(ManagerUsers user) {
		this.id = user.getId();
		this.name = user.getName();
		this.qualification = user.getQualification();
		this.email = user.getEmail();
		this.admin = user.getAdmin() != null ? user.getAdmin().getName() : null;
	}

	public ManagerUserAssignDto(ManagerStudents s) {
		this.id = s.getId();
		this.name = s.getName();
		this.email = s.getEmail();
		this.qualification = s.getQualification();
		this.admin = s.getAdmin() != null ? s.getAdmin().getName() : null;
		this.assignedDate = s.getAssignDate();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

}
