package com.careerconnect.dto;

import lombok.Data;

@Data
public class PostJobRequest {

    private String title;
    private String company;
    private String location;
    private String salary;
    private String type;
    private String description;
    private String requirements;
    private String department;
    
}
