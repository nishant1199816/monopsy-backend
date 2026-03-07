package com.monopsy.app.controller;

import com.monopsy.app.model.AppUser;
import com.monopsy.app.repository.AppUserRepository;
import com.monopsy.app.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notifService;
    private final AppUserRepository userRepo;

    public NotificationController(NotificationService notifService, AppUserRepository userRepo) {
        this.notifService = notifService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal String email) {
        AppUser user = userRepo.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(notifService.getForUser(user.getId()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> unreadCount(@AuthenticationPrincipal String email) {
        AppUser user = userRepo.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(Map.of("count", notifService.countUnread(user.getId())));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<?> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(notifService.markRead(id));
    }

    @PostMapping("/read-all")
    public ResponseEntity<?> markAllRead(@AuthenticationPrincipal String email) {
        AppUser user = userRepo.findByEmail(email).orElseThrow();
        int count = notifService.markAllRead(user.getId());
        return ResponseEntity.ok(Map.of("marked", count));
    }
}
