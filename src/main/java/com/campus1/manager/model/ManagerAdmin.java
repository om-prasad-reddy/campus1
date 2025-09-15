package com.campus1.manager.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class ManagerAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = false, nullable = false)
    private String phone; // Changed from Long to String to match Profile_Admin

    private String designation;

    @Column(name = "password")
    private String pwd;

    private String address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private ManagerManager manager;

    @OneToMany(mappedBy = "admin")
    private List<ManagerUsers> users;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ManagerAdminTicket> tickets;

    // Getters and Setters
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ManagerManager getManager() {
        return manager;
    }

    public void setManager(ManagerManager manager) {
        this.manager = manager;
    }

    public List<ManagerUsers> getUsers() {
        return users;
    }

    public void setUsers(List<ManagerUsers> users) {
        this.users = users;
    }

    public List<ManagerAdminTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<ManagerAdminTicket> tickets) {
        this.tickets = tickets;
    }
}