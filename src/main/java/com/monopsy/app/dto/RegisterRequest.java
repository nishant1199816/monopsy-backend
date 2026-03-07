package com.monopsy.app.dto;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank @Size(min = 6) private String password;
    private String role = "ROLE_WORKER";

    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }
    public void setName(String v)     { this.name = v; }
    public void setEmail(String v)    { this.email = v; }
    public void setPassword(String v) { this.password = v; }
    public void setRole(String v)     { this.role = v; }
}
