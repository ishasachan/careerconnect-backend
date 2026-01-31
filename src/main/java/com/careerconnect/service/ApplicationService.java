package com.careerconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.ApplyRequest;
import com.careerconnect.model.JobApplication;
import com.careerconnect.repository.JobApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

 private final JobApplicationRepository repo;


 // Apply
 public String apply(ApplyRequest req){

  // Check duplicate
  boolean exists =
   repo.findByUserIdAndJobId(
     req.getUserId(),
     req.getJobId()
   ).isPresent();

  if(exists){
   return "ALREADY_APPLIED";
  }

  JobApplication app = new JobApplication();

  app.setUserId(req.getUserId());
  app.setJobId(req.getJobId());

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
 public List<JobApplication> getByUser(Long userId){

  return repo.findByUserId(userId);
 }
}
