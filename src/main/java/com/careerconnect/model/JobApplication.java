package com.careerconnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="job_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Long userId;

 @ManyToOne
@JoinColumn(name = "job_id")
private Job job;


 private String fullName;

 private String email;

 private String phone;

 private Integer yearsOfExperience;

 private String currentCompany;

 @Column(columnDefinition="TEXT")
 private String resumeUrl;

 @Column(columnDefinition="TEXT")
 private String coverLetter;

 private String status;

 private LocalDateTime appliedDate;
}
