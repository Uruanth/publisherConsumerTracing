package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
@Data
public abstract class Event<T> implements Serializable {

    public final Instant when;
    public final UUID uuid;
    public final SpanEvent span;
    public final String type;
    public final T body;

}