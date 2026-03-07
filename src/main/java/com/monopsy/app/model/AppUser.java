package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    private String phone;
    private String location;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public AppUser() {}

    public Long getId()           { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getPassword()   { return password; }
    public Role getRole()         { return role; }
    public String getPhone()      { return phone; }
    public String getLocation()   { return location; }
    public Instant getCreatedAt() { return createdAt; }

    public void setId(Long id)           { this.id = id; }
    public void setName(String name)     { this.name = name; }
    public void setEmail(String email)   { this.email = email; }
    public void setPassword(String pass) { this.password = pass; }
    public void setRole(Role role)       { this.role = role; }
    public void setPhone(String phone)   { this.phone = phone; }
    public void setLocation(String loc)  { this.location = loc; }

    public boolean isWorker()   { return role == Role.ROLE_WORKER; }
    public boolean isEmployer() { return role == Role.ROLE_EMPLOYER; }
    public boolean isAdmin()    { return role == Role.ROLE_ADMIN; }

    public void setRoleFromString(String raw) {
        if (raw == null || raw.isBlank()) { this.role = Role.ROLE_USER; return; }
        String n = raw.trim().toUpperCase();
        if (!n.startsWith("ROLE_")) n = "ROLE_" + n;
        try { this.role = Role.valueOf(n); }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + raw);
        }
    }
}
