package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.model.event;

import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.Event;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.SpanEvent;
import lombok.Getter;

@Getter
public class EventOutput extends Event<DataEvent> {
    public EventOutput(SpanEvent span, String type, DataEvent body) {
        super(span, type, body);
    }
}
