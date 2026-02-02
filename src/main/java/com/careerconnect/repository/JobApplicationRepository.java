package com.careerconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.JobApplication;

public interface JobApplicationRepository
  extends JpaRepository<JobApplication,Long>{

 List<JobApplication> findByUserId(Long userId);

 Optional<JobApplication>
 findByUserIdAndJob_Id(Long userId,Long jobId);

 List<JobApplication> findByJob_RecruiterId(Long recruiterId);

List<JobApplication> findByJob_Id(Long jobId);

List<JobApplication> findByJob_RecruiterIdAndJob_Id(Long recruiterId, Long jobId);

}
