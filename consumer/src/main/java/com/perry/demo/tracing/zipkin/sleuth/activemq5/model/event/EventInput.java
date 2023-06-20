package com.perry.demo.tracing.zipkin.sleuth.activemq5.model.event;


import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.Event;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.SpanEvent;

public class EventInput extends Event<DataEvent> {
    public EventInput(SpanEvent span, String type, DataEvent body) {
        super(null, null, span, type, body);
    }

    public EventInput() {
        super(null, null, null, null, null);
    }

}
