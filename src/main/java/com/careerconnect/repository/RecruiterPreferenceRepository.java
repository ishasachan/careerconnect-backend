package com.careerconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.RecruiterPreference;

public interface RecruiterPreferenceRepository extends JpaRepository<RecruiterPreference, Long> {

    Optional<RecruiterPreference> findByRecruiterId(Long recruiterId);
}
