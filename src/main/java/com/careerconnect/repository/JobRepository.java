package com.careerconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

 // Search + filter
 List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
   String t,
   String c,
   String l
 );

 List<Job> findByType(String type);

 List<Job> findByLocation(String location);
}
