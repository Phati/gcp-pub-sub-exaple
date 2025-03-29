package com.learn.cloud.gcp.pubsub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.cloud.gcp.pubsub.constants.ApplicationConstants;
import com.learn.cloud.gcp.pubsub.model.BaseEvent;
import com.learn.cloud.gcp.pubsub.model.Order;
import com.learn.cloud.gcp.pubsub.model.OrderResponse;
import com.learn.cloud.gcp.pubsub.publisher.GenericMessagePublisher;
import com.learn.cloud.gcp.pubsub.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderService {

    private final GenericMessagePublisher genericMessagePublisher;
    private final String projectId;
    private final String topicName;


    public OrderService(GenericMessagePublisher genericMessagePublisher,
                        @Value("${gcp.project.name}") String projectId,
                        @Value("${gcp.topic.name.order}") String topicName) {
        this.genericMessagePublisher = genericMessagePublisher;
        this.projectId = projectId;
        this.topicName = topicName;
    }

    public OrderResponse createOrder(Order order) {
        BaseEvent<Order> generateResumeEvent = new BaseEvent<>();
        generateResumeEvent.setData(order);
        generateResumeEvent.setEventId("test1234");
        generateResumeEvent.setReferenceNo("test1234");
        generateResumeEvent.setType(ApplicationConstants.CREATE_ORDER_EVENT_TYPE);
        generateResumeEvent.setSource("pub-sub-service");

        String messageId = null;
        try {
            messageId = genericMessagePublisher.publishWithOrderingKey(projectId, topicName, CommonUtils._mapper.writeValueAsString(generateResumeEvent), order.getProductId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Request to Create Order successfully submitted with referenceNo: {} , order_id: {}", generateResumeEvent.getReferenceNo(),order.getOrderId());

        return OrderResponse.builder()
                .messageId(messageId)
                .referenceNo(generateResumeEvent.getReferenceNo())
                .message("Request submitted successfully, you will receive your resume on your email.")
                .build();
    }
}
