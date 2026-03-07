package com.monopsy.app.dto;

public class LoginResponse {
    private String status, message, token, role, name;
    private Long userId;
    public LoginResponse() {}
    public LoginResponse(String status, String message, String token, String role, Long userId, String name) {
        this.status=status; this.message=message; this.token=token;
        this.role=role; this.userId=userId; this.name=name;
    }
    public String getStatus()  { return status; }
    public String getMessage() { return message; }
    public String getToken()   { return token; }
    public String getRole()    { return role; }
    public Long getUserId()    { return userId; }
    public String getName()    { return name; }
}
