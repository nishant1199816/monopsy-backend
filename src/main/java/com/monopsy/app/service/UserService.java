package com.monopsy.app.service;

import com.monopsy.app.config.JwtUtil;
import com.monopsy.app.dto.*;
import com.monopsy.app.model.AppUser;
import com.monopsy.app.model.Role;
import com.monopsy.app.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AppUserRepository userRepo;
    private final JwtUtil           jwtUtil;
    private final PasswordEncoder   encoder = new BCryptPasswordEncoder();

    public UserService(AppUserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil  = jwtUtil;
    }

    // ── Register ─────────────────────────────────────────────
    public UserResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        AppUser user = new AppUser();
        user.setName(req.getName());
        user.setEmail(req.getEmail().toLowerCase().trim());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRoleFromString(req.getRole());

        return UserResponse.from(userRepo.save(user));
    }

    // ── Login ────────────────────────────────────────────────
    public LoginResponse login(LoginRequest req) {
        AppUser user = userRepo.findByEmail(req.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid email or password");

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new LoginResponse(
                "success",
                "Login successful",
                token,
                user.getRole().name(),
                user.getId(),
                user.getName()
        );
    }

    // ── Helpers ──────────────────────────────────────────────
    public AppUser getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<AppUser> findAll() {
        return userRepo.findAll();
    }
}
