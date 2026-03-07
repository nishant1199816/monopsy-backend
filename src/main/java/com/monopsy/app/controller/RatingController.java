package com.monopsy.app.controller;

import com.monopsy.app.dto.RatingRequest;
import com.monopsy.app.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/job/{jobId}")
    public ResponseEntity<?> rate(@PathVariable Long jobId,
                                  @Valid @RequestBody RatingRequest req,
                                  @AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(ratingService.rateWorker(jobId, req, email)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @GetMapping("/worker/{userId}")
    public ResponseEntity<?> workerRatings(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getRatingsForWorker(userId));
    }
}
