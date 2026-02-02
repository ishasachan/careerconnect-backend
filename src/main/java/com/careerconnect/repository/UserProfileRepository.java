package com.careerconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerconnect.model.User;
import com.careerconnect.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
     Optional<UserProfile> findByUser(User user);
}
