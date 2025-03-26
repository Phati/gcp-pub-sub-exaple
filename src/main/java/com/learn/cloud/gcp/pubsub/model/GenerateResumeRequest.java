package com.learn.cloud.gcp.pubsub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GenerateResumeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private List<String> Skills;
}
