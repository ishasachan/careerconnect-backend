package com.careerconnect.dto;

import lombok.Data;

@Data
public class MatchResponse {

    private Integer matchScore;

    private Integer skillScore;
    private Integer experienceScore;
    private Integer profileScore;

    private String feedback;
}
