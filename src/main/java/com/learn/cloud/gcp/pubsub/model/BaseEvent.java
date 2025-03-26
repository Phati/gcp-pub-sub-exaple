package com.learn.cloud.gcp.pubsub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseEvent<T> implements Serializable {
    private String type;
    private String source;
    private String timeStamp;
    private String action;
    private String eventId;
    private String referenceNo;
    private T data;
}
