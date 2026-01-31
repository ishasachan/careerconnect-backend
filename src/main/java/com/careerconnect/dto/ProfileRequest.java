package com.careerconnect.dto;

import lombok.Data;

@Data
public class ProfileRequest {
    private Long userId;

    private String bio;

    private String skills;

    private String resumeUrl;

    private String avatarUrl;

    private String aiFeedback;
}
