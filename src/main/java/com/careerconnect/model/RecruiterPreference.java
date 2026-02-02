package com.careerconnect.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recruiter_preferences")
@Data
public class RecruiterPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recruiterId;

    @Column(columnDefinition = "TEXT")
    private String roles;

    @Column(columnDefinition = "TEXT")
    private String experienceLevels;

    @Column(columnDefinition = "TEXT")
    private String locations;
}
