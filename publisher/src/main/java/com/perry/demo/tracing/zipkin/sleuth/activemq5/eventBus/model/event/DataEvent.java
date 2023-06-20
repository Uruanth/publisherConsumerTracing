package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataEvent {

    private String foo;
    private Long bar;

}
