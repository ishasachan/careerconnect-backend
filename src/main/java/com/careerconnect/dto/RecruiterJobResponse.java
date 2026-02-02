package com.careerconnect.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RecruiterJobResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String type;
    private String salary;
    private String status;
    private LocalDate postedDate;

    private Long applicantsCount; // ⭐ important
}
