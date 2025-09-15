package com.campus1.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Table(name = "users")
public class ManagerUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private long phone;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private ManagerManager manager;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private ManagerAdmin admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ManagerStudents> applications;

    // 👇 Automatically fetch latest degree from user_education
    @Formula("(SELECT ue.degree " +
             "FROM user_education ue " +
             "WHERE ue.user_id = id " +
             "ORDER BY ue.end_year DESC LIMIT 1)")
    private String qualification;

    // ---------------- Getters & Setters ----------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    // Full name
    public String getName() {
        if (firstName == null && lastName == null) return null;
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;
        return firstName + " " + lastName;
    }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            String[] parts = name.trim().split(" ", 2);
            this.firstName = parts[0];
            this.lastName = parts.length > 1 ? parts[1] : "";
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public long getPhone() { return phone; }
    public void setPhone(long phone) { this.phone = phone; }

    public ManagerManager getManager() { return manager; }
    public void setManager(ManagerManager manager) { this.manager = manager; }

    public ManagerAdmin getAdmin() { return admin; }
    public void setAdmin(ManagerAdmin admin) { this.admin = admin; }

    public List<ManagerStudents> getApplications() { return applications; }
    public void setApplications(List<ManagerStudents> applications) { this.applications = applications; }
}
