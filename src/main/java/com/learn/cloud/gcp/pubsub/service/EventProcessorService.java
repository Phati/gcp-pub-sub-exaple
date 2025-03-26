package com.learn.cloud.gcp.pubsub.service;

import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeRequest;
import com.learn.cloud.gcp.pubsub.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EventProcessorService {

    public void processGenerateResumeEvent(BaseEvent<GenerateResumeRequest> generateResumeEvent) {
        log.info("Resume builder request to be processed- id: {}", generateResumeEvent.getData().getId());
    }

    public void processCreateOrderEvent(BaseEvent<Order> createOrderEvent) {
        log.info("Create Order request to be processed- order id: {}", createOrderEvent.getData().getId());
    }
}
