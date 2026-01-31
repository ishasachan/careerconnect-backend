package com.careerconnect.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
@Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @OneToOne
 @JoinColumn(name = "user_id")
 private User user;

 @Column(columnDefinition = "TEXT")
 private String bio;

 private String skills;

 private String resumeUrl;

 private String avatarUrl;

 @Column(columnDefinition = "TEXT")
 private String aiFeedback; // JSON as String

 private LocalDateTime createdAt;

 private LocalDateTime updatedAt;

 @PrePersist
 public void onCreate() {
   createdAt = LocalDateTime.now();
   updatedAt = LocalDateTime.now();
 }

 @PreUpdate
 public void onUpdate() {
   updatedAt = LocalDateTime.now();
 }
}
