package com.monopsy.app.service;

import com.monopsy.app.dto.*;
import com.monopsy.app.model.*;
import com.monopsy.app.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerProfileService {

    private final WorkerProfileRepository profileRepo;
    private final AppUserRepository       userRepo;

    public WorkerProfileService(WorkerProfileRepository profileRepo,
                                AppUserRepository userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo    = userRepo;
    }

    // ── Create ───────────────────────────────────────────────
    public WorkerProfileResponse create(WorkerProfileRequest req, String email) {
        AppUser user = getUser(email);

        if (profileRepo.findByUserId(user.getId()).isPresent())
            throw new IllegalArgumentException("Worker profile already exists. Use update.");

        WorkerProfile p = buildProfile(new WorkerProfile(), req);
        p.setUser(user);
        return WorkerProfileResponse.from(profileRepo.save(p));
    }

    // ── Update (by logged-in worker) ─────────────────────────
    public WorkerProfileResponse update(WorkerProfileRequest req, String email) {
        AppUser user = getUser(email);
        WorkerProfile p = profileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found — create first"));

        return WorkerProfileResponse.from(profileRepo.save(buildProfile(p, req)));
    }

    // ── Get my profile ───────────────────────────────────────
    public WorkerProfileResponse getMyProfile(String email) {
        AppUser user = getUser(email);
        return WorkerProfileResponse.from(
                profileRepo.findByUserId(user.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Profile not found"))
        );
    }

    // ── Get by ID (public) ───────────────────────────────────
    public WorkerProfileResponse getById(Long id) {
        return WorkerProfileResponse.from(
                profileRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Profile not found"))
        );
    }

    // ── List all (public) ────────────────────────────────────
    public List<WorkerProfileResponse> listAll() {
        return profileRepo.findAll().stream().map(WorkerProfileResponse::from).toList();
    }

    // ── Search ───────────────────────────────────────────────
    public List<WorkerProfileResponse> search(String skill, String location) {
        String s = skill    == null ? "" : skill.trim();
        String l = location == null ? "" : location.trim();
        return profileRepo
                .findBySkillCategoryIgnoreCaseContainingAndLocationIgnoreCaseContaining(s, l)
                .stream().map(WorkerProfileResponse::from).toList();
    }

    // ── Helpers ──────────────────────────────────────────────
    private WorkerProfile buildProfile(WorkerProfile p, WorkerProfileRequest req) {
        if (req.getFullName()       != null) p.setFullName(req.getFullName());
        if (req.getSkillCategory()  != null) p.setSkillCategory(req.getSkillCategory());
        if (req.getExperienceYears()!= null) p.setExperienceYears(req.getExperienceYears());
        if (req.getDailyRate()      != null) p.setDailyRate(req.getDailyRate());
        if (req.getHourlyRate()     != null) p.setHourlyRate(req.getHourlyRate());
        if (req.getLocation()       != null) p.setLocation(req.getLocation());
        if (req.getBio()            != null) p.setBio(req.getBio());
        if (req.getPhone()          != null) p.setPhone(req.getPhone());
        if (req.getAvailability()   != null) p.setAvailability(req.getAvailability());
        return p;
    }

    private AppUser getUser(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
