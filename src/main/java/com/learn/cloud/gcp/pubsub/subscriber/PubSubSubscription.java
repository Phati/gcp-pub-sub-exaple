package com.learn.cloud.gcp.pubsub.subscriber;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.pubsub.v1.PubsubMessage;
import com.learn.cloud.gcp.pubsub.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;

@Log4j2
public abstract class PubSubSubscription<T> {

    private final PubSubTemplate pubSubTemplate;
    private final String subscription;
    private final TypeReference<T> typeReference;

    protected PubSubSubscription(PubSubTemplate pubSubTemplate, String subscription, TypeReference<T> typeReference) {
        this.pubSubTemplate = pubSubTemplate;
        this.subscription = subscription;
        this.typeReference = typeReference;
    }


    @PostConstruct
    private void subscribe() {
        pubSubTemplate.subscribe(subscription, message -> {
            try {
                PubsubMessage pubsubMessage = message.getPubsubMessage();
                process(CommonUtils._mapper.readValue(pubsubMessage.getData().toStringUtf8(), typeReference), message);
            } catch (Exception e) {
                onError(e, message);
            }
        });
    }

    public abstract void process(T event, BasicAcknowledgeablePubsubMessage message);

    public abstract void onError(Exception e, BasicAcknowledgeablePubsubMessage message);
}
