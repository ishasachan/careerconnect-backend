package com.careerconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.ApplyRequest;
import com.careerconnect.model.JobApplication;
import com.careerconnect.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {

  private final ApplicationService service;

  // APPLY
  @PostMapping("/apply")
  public ResponseEntity<ApiResponse> apply(
      @RequestBody ApplyRequest req) {

    if (req.getUserId() == null ||
        req.getJobId() == null) {

      return ResponseEntity.badRequest().body(
          new ApiResponse(false,
              "UserId & JobId required", null));
    }

    String result = service.apply(req);

    if ("JOB_NOT_FOUND".equals(result)) {
      return ResponseEntity.ok(
          new ApiResponse(false, "Job not found", null));
    }

    if ("ALREADY_APPLIED".equals(result)) {

      return ResponseEntity.ok(
          new ApiResponse(false,
              "Already applied", null));
    }

    return ResponseEntity.ok(
        new ApiResponse(true,
            "Application submitted", null));
  }

  // LIST
  @GetMapping("/user/{userId}")
  public ResponseEntity<ApiResponse> getUserApps(
      @PathVariable Long userId) {

    List<JobApplication> list = service.getByUser(userId);

    return ResponseEntity.ok(
        new ApiResponse(true,
            "Applications fetched",
            list));
  }

  /*
   * ===========================
   * LIST APPLICANTS
   * ===========================
   */
  @GetMapping("/recruiter/{recruiterId}")
  public ApiResponse getApplicants(

      @PathVariable Long recruiterId,

      // optional filter
      @RequestParam(required = false) Long jobId) {

    return service.getApplicants(recruiterId, jobId);
  }

  /*
   * ===========================
   * UPDATE STATUS
   * ===========================
   */
  @PatchMapping("/{id}/status")
  public ApiResponse updateStatus(

      @PathVariable Long id,

      @RequestParam String status) {

    return service.updateStatus(id, status);
  }

  /*
   * ===========================
   * RESET STATUS
   * ===========================
   */
  @PatchMapping("/{id}/reset")
  public ApiResponse resetStatus(@PathVariable Long id) {

    return service.resetStatus(id);
  }
}
