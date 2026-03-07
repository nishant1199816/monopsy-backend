package com.monopsy.app.controller;

import com.monopsy.app.dto.*;
import com.monopsy.app.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // GET /api/jobs — public
    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(jobService.listAll());
    }

    // GET /api/jobs/{id} — public
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try { return ResponseEntity.ok(jobService.getById(id)); }
        catch (Exception e) { return ResponseEntity.notFound().build(); }
    }

    // POST /api/jobs — employer posts a job
    @PostMapping
    public ResponseEntity<?> createJob(@Valid @RequestBody CreateJobRequest req,
                                       @AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(jobService.createJob(req, email)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // GET /api/jobs/my — employer sees own jobs
    @GetMapping("/my")
    public ResponseEntity<?> myJobs(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(jobService.getMyJobs(email));
    }

    // GET /api/jobs/{id}/applications — employer views applications
    @GetMapping("/{id}/applications")
    public ResponseEntity<?> applications(@PathVariable Long id,
                                          @AuthenticationPrincipal String email) {
        try { return ResponseEntity.ok(jobService.getApplications(id, email)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // POST /api/jobs/{id}/apply — worker applies
    @PostMapping("/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Long id,
                                   @AuthenticationPrincipal String email,
                                   @RequestBody(required = false) Map<String, String> body) {
        try {
            String message = body != null ? body.get("message") : null;
            jobService.apply(id, email, message);
            return ResponseEntity.ok("Application submitted");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // POST /api/jobs/applications/{appId}/accept
    @PostMapping("/applications/{appId}/accept")
    public ResponseEntity<?> accept(@PathVariable Long appId,
                                    @AuthenticationPrincipal String email) {
        try { jobService.decideApplication(appId, true, email); return ResponseEntity.ok("Accepted"); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // POST /api/jobs/applications/{appId}/reject
    @PostMapping("/applications/{appId}/reject")
    public ResponseEntity<?> reject(@PathVariable Long appId,
                                    @AuthenticationPrincipal String email) {
        try { jobService.decideApplication(appId, false, email); return ResponseEntity.ok("Rejected"); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // POST /api/jobs/{id}/complete — employer marks job complete
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeByEmployer(@PathVariable Long id,
                                                @AuthenticationPrincipal String email) {
        try { jobService.completeJob(id, email); return ResponseEntity.ok("Job marked complete"); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // POST /api/jobs/{id}/worker-complete — worker marks job complete
    @PostMapping("/{id}/worker-complete")
    public ResponseEntity<?> completeByWorker(@PathVariable Long id,
                                              @AuthenticationPrincipal String email) {
        try { jobService.workerCompleteJob(id, email); return ResponseEntity.ok("Job marked complete"); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // GET /api/jobs/worker/assigned — worker's assigned jobs
    @GetMapping("/worker/assigned")
    public ResponseEntity<?> myAssigned(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(jobService.getMyAssignedJobs(email));
    }
}
