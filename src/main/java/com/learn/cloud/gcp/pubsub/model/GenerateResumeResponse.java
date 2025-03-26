package com.learn.cloud.gcp.pubsub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GenerateResumeResponse {
    private String message;
    private String referenceNo;
    private String messageId;
}

