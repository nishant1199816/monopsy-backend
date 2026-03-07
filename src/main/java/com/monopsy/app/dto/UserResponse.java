package com.monopsy.app.dto;
import com.monopsy.app.model.AppUser;
import java.time.Instant;

public class UserResponse {
    private Long id;
    private String name, email, role;
    private Instant createdAt;

    public static UserResponse from(AppUser u) {
        UserResponse r = new UserResponse();
        r.id = u.getId(); r.name = u.getName();
        r.email = u.getEmail(); r.role = u.getRole().name();
        r.createdAt = u.getCreatedAt();
        return r;
    }
    public Long getId()           { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getRole()       { return role; }
    public Instant getCreatedAt() { return createdAt; }
}
