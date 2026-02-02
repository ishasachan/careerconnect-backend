package com.careerconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.ApplyRequest;
import com.careerconnect.model.Job;
import com.careerconnect.model.JobApplication;
import com.careerconnect.repository.JobApplicationRepository;
import com.careerconnect.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final JobApplicationRepository repo;
  private final JobRepository jobRepo;

  // Apply
  public String apply(ApplyRequest req) {

    // Check duplicate
    boolean exists = repo.findByUserIdAndJob_Id(
        req.getUserId(),
        req.getJobId()).isPresent();

    if (exists) {
      return "ALREADY_APPLIED";
    }

    Job job = jobRepo
        .findById(req.getJobId())
        .orElse(null);

    if (job == null) {
      return "JOB_NOT_FOUND";
    }

    JobApplication app = new JobApplication();

    app.setUserId(req.getUserId());
    app.setJob(job);
    app.setFullName(req.getFullName());
    app.setEmail(req.getEmail());
    app.setPhone(req.getPhone());

    app.setYearsOfExperience(req.getYearsOfExperience());
    app.setCurrentCompany(req.getCurrentCompany());

    app.setResumeUrl(req.getResumeUrl());
    app.setCoverLetter(req.getCoverLetter());

    app.setStatus("APPLIED");
    app.setAppliedDate(LocalDateTime.now());

    repo.save(app);

    return "SUCCESS";
  }

  // Get user applications
  public List<JobApplication> getByUser(Long userId) {

    return repo.findByUserId(userId);
  }

  /*
   * ===========================
   * GET ALL APPLICANTS
   * ===========================
   */
  public ApiResponse getApplicants(Long recruiterId, Long jobId) {

    List<JobApplication> list;

    // Filter by job
    if (jobId != null) {
      list = repo.findByJob_RecruiterIdAndJob_Id(recruiterId, jobId);
    }
    // All recruiter jobs
    else {
      list = repo.findByJob_RecruiterId(recruiterId);
    }

    return new ApiResponse(true, "Applicants loaded", list);
  }

  /*
   * ===========================
   * UPDATE STATUS
   * ===========================
   */
  public ApiResponse updateStatus(Long id, String status) {

    JobApplication app = repo.findById(id).orElse(null);

    if (app == null) {
      return new ApiResponse(false, "Application not found", null);
    }

    app.setStatus(status.toUpperCase());

    repo.save(app);

    return new ApiResponse(true,
        "Status updated",
        app);
  }

  /*
   * ===========================
   * RESET STATUS
   * ===========================
   */
  public ApiResponse resetStatus(Long id) {

    JobApplication app = repo.findById(id).orElse(null);

    if (app == null) {
      return new ApiResponse(false, "Application not found", null);
    }

    app.setStatus("APPLIED");

    repo.save(app);

    return new ApiResponse(true,
        "Status reset",
        app);
  }
}
