package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics.ISender;
import com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.model.event.EventOutput;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@Log
public class Producer implements ISender<EventOutput> {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${app.bus.queue}")
    private String queue;



    @Override
    public void sendEvent(EventOutput event) {
        log.info("Sending message " + event.getUuid() + ", to queue - " + queue);

        jmsTemplate.send(queue, session -> {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new ConfigGsonLocalDateTime())
                    .registerTypeAdapter(Instant.class, new ConfigGsonInstant())
                    .create();

            TextMessage messageToSend = session.createTextMessage(gson.toJson(event));
            return messageToSend;
        });
    }
}
