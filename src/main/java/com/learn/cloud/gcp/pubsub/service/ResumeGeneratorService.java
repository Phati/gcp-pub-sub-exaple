package com.learn.cloud.gcp.pubsub.service;

import com.learn.cloud.gcp.pubsub.constants.ApplicationConstants;
import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeRequest;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeResponse;
import com.learn.cloud.gcp.pubsub.publisher.GenericMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResumeGeneratorService {

    private final GenericMessagePublisher genericMessagePublisher;
    private final String projectId;
    private final String topicName;

    public ResumeGeneratorService(GenericMessagePublisher genericMessagePublisher,
                                  @Value("${gcp.project.name}") String projectId,
                                  @Value("${gcp.topic.name.resume.builder}") String topicName) {
        this.genericMessagePublisher = genericMessagePublisher;
        this.projectId = projectId;
        this.topicName = topicName;
    }

    public GenerateResumeResponse publishGenerateResumeEvent(GenerateResumeRequest generateResumeRequest) {
        BaseEvent<GenerateResumeRequest> generateResumeEvent = new BaseEvent<>();
        generateResumeEvent.setData(generateResumeRequest);
        generateResumeEvent.setEventId("test1234");
        generateResumeEvent.setReferenceNo("test1234");
        generateResumeEvent.setType(ApplicationConstants.GENERATE_RESUME_EVENT_TYPE);
        generateResumeEvent.setSource("pub-sub-service");

        String messageId = genericMessagePublisher.publish(projectId, topicName, generateResumeEvent, null);
        log.info("Request to generate resume successfully submitted with referenceNo: {}", generateResumeEvent.getReferenceNo());
        return GenerateResumeResponse.builder()
                .messageId(messageId)
                .referenceNo(generateResumeEvent.getReferenceNo())
                .message("Request submitted successfully, you will receive your resume on your email.")
                .build();
    }
}
