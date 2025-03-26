package com.learn.cloud.gcp.pubsub.subscriber;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.pubsub.v1.PubsubMessage;
import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;

@Log4j2
public abstract class PubSubSubscription<T> {

    private final PubSubTemplate pubSubTemplate;
    private final String subscription;

    protected PubSubSubscription(PubSubTemplate pubSubTemplate, String subscription) {
        this.pubSubTemplate = pubSubTemplate;
        this.subscription = subscription;
    }


    @PostConstruct
    private void subscribe() {
        pubSubTemplate.subscribe(subscription, message -> {
            try {
                PubsubMessage pubsubMessage = message.getPubsubMessage();
                BaseEvent<T> event = CommonUtils._mapper.readValue(pubsubMessage.getData().toStringUtf8(), new TypeReference<>() {
                });
                process(event, message);
            } catch (Exception e) {
                onError(e, message);
            }
        });
    }

    public abstract void process(BaseEvent<T> event, BasicAcknowledgeablePubsubMessage message);

    public abstract void onError(Exception e, BasicAcknowledgeablePubsubMessage message);
}
