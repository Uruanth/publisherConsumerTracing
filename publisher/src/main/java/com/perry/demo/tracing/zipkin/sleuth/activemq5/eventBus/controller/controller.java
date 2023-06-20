package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.controller;

import brave.Span;
import brave.Tracer;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config.Producer;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.SpanEvent;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.model.event.DataEvent;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.model.event.EventOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class controller {

    @Autowired
    Producer producer;

    @Autowired
    Tracer tracer;

    @GetMapping("/send/event")
    public String sendEvent() {
        Span span = tracer.nextSpan()
                .name("Publisher")
                .tag("send event", "foo")
                .annotate("anotate")
                .error(new RuntimeException("error tracer"))
                .kind(Span.Kind.CLIENT)
                .start();
            SpanEvent spanEv = new SpanEvent(
                    span.context().parentId(),
                    span.context().spanId(),
                    span.context().traceId()
            );
            EventOutput event = new EventOutput(
                    spanEv, "Consumer",
                    new DataEvent(
                            "foo",
                            100l
                    ));

            producer.sendEvent(event);
        span.finish();
        return "funciono";
    }
}
