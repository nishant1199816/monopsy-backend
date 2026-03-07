package com.monopsy.app.controller;

import com.monopsy.app.dto.*;
import com.monopsy.app.service.WorkerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workers")
public class WorkerProfileController {

    private final WorkerProfileService profileService;

    public WorkerProfileController(WorkerProfileService profileService) {
        this.profileService = profileService;
    }

    // GET /api/workers — public
    @GetMapping
    public ResponseEntity<?> listAll(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String location) {
        if (skill != null || location != null)
            return ResponseEntity.ok(profileService.search(skill, location));
        return ResponseEntity.ok(profileService.listAll());
    }

    // GET /api/workers/{id} — public
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try { return ResponseEntity.ok(profileService.getById(id)); }
        catch (Exception e) { return ResponseEntity.notFound().build(); }
    }

    // GET /api/workers/me — logged-in worker's own profile
    @GetMapping("/me")
    public ResponseEntity<?> myProfile(@AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(profileService.getMyProfile(email)); }
        catch (Exception e) { return ResponseEntity.notFound().build(); }
    }

    // POST /api/workers — create profile
    @PostMapping
    public ResponseEntity<?> create(@RequestBody WorkerProfileRequest req,
                                    @AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(profileService.create(req, email)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // PUT /api/workers/me — update own profile
    @PutMapping("/me")
    public ResponseEntity<?> update(@RequestBody WorkerProfileRequest req,
                                    @AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(profileService.update(req, email)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // GET /api/workers/my-jobs — worker's assigned jobs (legacy path support)
    @GetMapping("/my-jobs")
    public ResponseEntity<?> myJobs(@AuthenticationPrincipal String email) {
        // delegated to JobService via controller — import JobService here if needed
        return ResponseEntity.ok(java.util.List.of());
    }
}
