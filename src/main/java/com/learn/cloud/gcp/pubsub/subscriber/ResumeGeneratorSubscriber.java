package com.learn.cloud.gcp.pubsub.subscriber;

import com.fasterxml.jackson.core.type.TypeReference;
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
public class ResumeGeneratorSubscriber extends PubSubSubscription<BaseEvent<GenerateResumeRequest>> {

    private final EventProcessorService eventProcessorService;

    public ResumeGeneratorSubscriber(PubSubTemplate pubSubTemplate,
                                     @Value("${gcp.subscription.name.resume.builder}") String subscription,
                                     EventProcessorService eventProcessorService) {
        super(pubSubTemplate, subscription, new TypeReference<>() {
        });
        this.eventProcessorService = eventProcessorService;
    }

    @Override
    public void process(BaseEvent<GenerateResumeRequest> event, BasicAcknowledgeablePubsubMessage message) {
        log.debug("received event. type:{}, referenceNo: {}", event.getType(), event.getReferenceNo());
        if (event.getType().equalsIgnoreCase(ApplicationConstants.GENERATE_RESUME_EVENT_TYPE)) {
            eventProcessorService.processGenerateResumeEvent(event);
        }
        message.ack();
    }

    @Override
    public void onError(Exception e, BasicAcknowledgeablePubsubMessage message) {
        log.debug("--------error: {}", e.getMessage());
        message.nack();
    }
}
