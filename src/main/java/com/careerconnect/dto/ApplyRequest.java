package com.careerconnect.dto;

import lombok.Data;

@Data
public class ApplyRequest {

 private Long userId;
 private Long jobId;

 private String fullName;
 private String email;
 private String phone;

 private Integer yearsOfExperience;
 private String currentCompany;

 private String resumeUrl;
 private String coverLetter;
}
