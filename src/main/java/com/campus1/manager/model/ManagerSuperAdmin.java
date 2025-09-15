package com.campus1.manager.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "SuperAdmin")
public class ManagerSuperAdmin {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 private String name;
	 @Column(unique = true, nullable = false)
	 private String email;

	 @OneToMany(mappedBy = "superAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<ManagerManager> managers;
	 
	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }

	 public String getName() {
		 return name;
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

	 public List<ManagerManager> getManager() {
		 return managers;
	 }

	 public void setManager(List<ManagerManager> managers) {
		 this.managers = managers;
	 }
}
