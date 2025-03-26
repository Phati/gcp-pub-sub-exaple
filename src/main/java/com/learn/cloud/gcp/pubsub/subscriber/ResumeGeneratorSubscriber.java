package com.learn.cloud.gcp.pubsub.subscriber;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.learn.cloud.gcp.pubsub.constants.ApplicationConstants;
import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.GenerateResumeRequest;
import com.learn.cloud.gcp.pubsub.service.EventProcessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ResumeGeneratorSubscriber extends PubSubSubscription<GenerateResumeRequest> {

    private final EventProcessorService eventProcessorService;

    public ResumeGeneratorSubscriber(PubSubTemplate pubSubTemplate,
                                     @Value("${gcp.subscription.name.resume.builder}") String subscription,
                                     EventProcessorService eventProcessorService) {
        super(pubSubTemplate, subscription);
        this.eventProcessorService = eventProcessorService;
    }

    @Override
    public void process(BaseEvent<GenerateResumeRequest> event, BasicAcknowledgeablePubsubMessage message) {
        log.info("received event. type:{}, referenceNo: {}", event.getType(), event.getReferenceNo());
        if (event.getType().equalsIgnoreCase(ApplicationConstants.GENERATE_RESUME_EVENT_TYPE)) {
            eventProcessorService.processGenerateResumeEvent(event);
        }
        message.ack();
    }

    @Override
    public void onError(Exception e, BasicAcknowledgeablePubsubMessage message) {
        log.info("--------error: {}", e.getMessage());
        message.nack();
    }
}
