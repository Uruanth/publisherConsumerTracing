package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpanEvent {
    private Long parentId;
    private Long spanId;
    private Long traceId;
}
