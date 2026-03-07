package com.monopsy.app.dto;
import jakarta.validation.constraints.*;

public class LoginRequest {
    @Email @NotBlank public String email;
    @NotBlank        public String password;
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public void setEmail(String v)    { this.email = v; }
    public void setPassword(String v) { this.password = v; }
}
