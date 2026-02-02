package com.careerconnect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Long recruiterId;

 private String title;

 private String company;

 private String location;

 private String salary;

 private String type;

 @Column(columnDefinition = "TEXT")
 private String description;

 @Column(columnDefinition = "TEXT")
 private String requirements; // comma separated

 private String department;

private Integer applicantsCount = 0;

private LocalDate postedDate = LocalDate.now();

private LocalDateTime createdAt = LocalDateTime.now();

private String status = "ACTIVE";

}
