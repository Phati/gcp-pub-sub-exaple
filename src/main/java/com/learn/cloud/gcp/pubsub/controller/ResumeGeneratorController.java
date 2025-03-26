package com.learn.cloud.gcp.pubsub.controller;

import com.learn.cloud.gcp.pubsub.model.GenerateResumeRequest;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeResponse;
import com.learn.cloud.gcp.pubsub.service.ResumeGeneratorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class ResumeGeneratorController {

    private final ResumeGeneratorService resumeGeneratorService;

    public ResumeGeneratorController(ResumeGeneratorService resumeGeneratorService) {
        this.resumeGeneratorService = resumeGeneratorService;
    }

    @PostMapping("/generate-resume")
    public ResponseEntity<GenerateResumeResponse> generateResume(@RequestBody GenerateResumeRequest generateResumeRequest) {
        GenerateResumeResponse generateResumeResponse = resumeGeneratorService.publishGenerateResumeEvent(generateResumeRequest);
        return new ResponseEntity<>(generateResumeResponse, HttpStatus.OK);
    }
}
