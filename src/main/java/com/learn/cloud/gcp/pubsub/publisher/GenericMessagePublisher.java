package com.learn.cloud.gcp.pubsub.publisher;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
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

    public <T> String publish(String projectId, String topic, String message) {
        TopicName topicName = TopicName.of(projectId, topic);
        AtomicReference<String> referenceNo = new AtomicReference<>("");
        PubsubMessage.Builder pubSubMessageBuilder = PubsubMessage.newBuilder();
        pubSubMessageBuilder.setData(ByteString.copyFromUtf8(message));
        CompletableFuture<String> future = pubSubTemplate.publish(topicName.toString(), pubSubMessageBuilder.build());
        future.whenComplete((messageId, throwable) -> {
            if (Objects.isNull(throwable)) {
                log.debug("Successfully published with messageId: {}", messageId);
                referenceNo.set(messageId);
            } else {
                log.error("Exception while publishing");
            }
        });

        return referenceNo.get();
    }

    public <T> String publishWithOrderingKey(String projectId, String topic, String message, String orderingKey) {
        TopicName topicName = TopicName.of(projectId, topic);
        AtomicReference<String> referenceNo = new AtomicReference<>("");
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(ByteString.copyFromUtf8(message))
                .setOrderingKey(orderingKey).build();
        Publisher publisher = null;
        try{
            publisher =
                    Publisher.newBuilder(topicName)
                            .setEndpoint("us-east1-pubsub.googleapis.com:443")
                            .setEnableMessageOrdering(true)
                            .build();

            ApiFuture<String> future = publisher.publish(pubsubMessage);
            future.addListener(() -> {
                try {
                    referenceNo.set(future.get());
                    System.out.println("Message published successfully: " + referenceNo.get());
                } catch (Exception e) {
                    System.err.println("Failed to publish message: " + e.getMessage());
                }
            }, Runnable::run);
        }catch (Exception e){
            log.error(e);
        }finally {
            if (Objects.nonNull(publisher))
                publisher.shutdown();
        }

        return referenceNo.get();

    }


}
