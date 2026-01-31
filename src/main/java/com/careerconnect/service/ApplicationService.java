package com.careerconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

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
 public String apply(ApplyRequest req){

  // Check duplicate
  boolean exists =
   repo.findByUserIdAndJob_Id(
     req.getUserId(),
     req.getJobId()
   ).isPresent();

  if(exists){
   return "ALREADY_APPLIED";
  }

   Job job = jobRepo
   .findById(req.getJobId())
   .orElse(null);

 if(job == null){
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
 public List<JobApplication> getByUser(Long userId){

  return repo.findByUserId(userId);
 }
}
