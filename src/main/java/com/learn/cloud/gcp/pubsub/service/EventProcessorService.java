package com.learn.cloud.gcp.pubsub.service;

import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EventProcessorService {

    public void processGenerateResumeEvent(BaseEvent<GenerateResumeRequest> generateResumeEvent) {
        log.info("Resume builder request to be processed");
    }
}
