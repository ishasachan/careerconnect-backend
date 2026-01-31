package com.careerconnect.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.JobApplication;

public interface JobApplicationRepository
  extends JpaRepository<JobApplication,Long>{

 List<JobApplication> findByUserId(Long userId);

 Optional<JobApplication>
 findByUserIdAndJobId(Long userId,Long jobId);
}
