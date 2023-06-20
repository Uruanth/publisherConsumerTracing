package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.IListener;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.model.event.EventInput;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.time.Instant;
import java.time.LocalDateTime;

@Log
@Component
@EnableJms
public class Listener implements IListener {

    @Autowired
    private Tracer tracer;

    @JmsListener(destination = "${app.bus.queue.listener}")
//    @SendTo("${app.bus.queue.outbound}")
    @Override
    public String processEvent(final Message jsonMessage) throws JMSException {
        log.info("Received message " + jsonMessage);
//        Span span = tracer.nextSpan()
//                .name("erv")
//                .tag("Consumiendo", "event")
//                .kind(Span.Kind.SERVER)
//                .start();
        String messageData = null;
        String response = null;
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            messageData = textMessage.getText();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new ConfigGsonLocalDateTime())
                    .registerTypeAdapter(Instant.class, new ConfigGsonInstant())
                    .create();
            EventInput event = gson.fromJson(messageData, EventInput.class);

            TraceContext context = TraceContext.newBuilder()
                    .parentId(event.getSpan().getParentId())
                    .spanId(event.getSpan().getSpanId())
                    .traceId(event.getSpan().getTraceId())
                    .build();
            Span span = tracer.newChild(context)
                    .name("Consumer")
                    .tag("eventId", event.getType())
                    .kind(Span.Kind.SERVER);
            span.finish();
        }
        return response;


    }


}
