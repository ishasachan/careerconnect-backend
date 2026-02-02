package com.careerconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.JobMatch;

public interface JobMatchRepository
        extends JpaRepository<JobMatch, Long> {

    Optional<JobMatch> findByUserIdAndJobId(Long userId, Long jobId);
}
