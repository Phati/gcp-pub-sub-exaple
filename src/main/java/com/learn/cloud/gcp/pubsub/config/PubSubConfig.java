package com.learn.cloud.gcp.pubsub.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.converter.JacksonPubSubMessageConverter;
import com.google.cloud.spring.pubsub.support.converter.PubSubMessageConverter;
import com.learn.cloud.gcp.pubsub.publisher.GenericMessagePublisher;
import com.learn.cloud.gcp.pubsub.utils.CommonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PubSubConfig {

    @Bean
    public PubSubMessageConverter pubSubMessageConverter() {
        return new JacksonPubSubMessageConverter(CommonUtils._mapper);
    }

    @Bean
    public GenericMessagePublisher genericMessagePublisher(PubSubTemplate pubSubTemplate) {
        return new GenericMessagePublisher(pubSubTemplate);
    }
}
