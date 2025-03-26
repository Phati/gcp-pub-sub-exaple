package com.learn.cloud.gcp.pubsub.subscriber;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.learn.cloud.gcp.pubsub.constants.ApplicationConstants;
import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.Order;
import com.learn.cloud.gcp.pubsub.service.EventProcessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderCreatorSubscriber extends PubSubSubscription<Order> {

    private final EventProcessorService eventProcessorService;

    public OrderCreatorSubscriber(PubSubTemplate pubSubTemplate,
                                  @Value("${gcp.subscription.order}") String subscriptionOk,
                                  EventProcessorService eventProcessorService) {
        super(pubSubTemplate, subscriptionOk);
        this.eventProcessorService = eventProcessorService;
    }

    @Override
    public void process(BaseEvent<Order> event, BasicAcknowledgeablePubsubMessage message) {
        if (event.getType().equalsIgnoreCase(ApplicationConstants.CREATE_ORDER_EVENT_TYPE)) {
            eventProcessorService.processCreateOrderEvent(event);
        }
        message.ack();
    }

    @Override
    public void onError(Exception e, BasicAcknowledgeablePubsubMessage message) {

    }
}
