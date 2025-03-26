package com.learn.cloud.gcp.pubsub.publisher;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.TopicName;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class GenericMessagePublisher {

    private final PubSubTemplate pubSubTemplate;

    public GenericMessagePublisher(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    public <T> String publish(String projectId, String topic, T message, String orderingKey) {
        TopicName topicName = TopicName.of(projectId, topic);
        AtomicReference<String> referenceNo = new AtomicReference<>("");
        CompletableFuture<String> future = pubSubTemplate.publish(topicName.toString(), message);
        future.whenComplete((messageId, throwable) -> {
            if (Objects.isNull(throwable)) {
                log.info("Successfully published parent child category with messageId: {}", messageId);
                referenceNo.set(messageId);
            } else {
                log.error("Exception while publishing the parent child category");
            }
        });

        return referenceNo.get();
    }
}
