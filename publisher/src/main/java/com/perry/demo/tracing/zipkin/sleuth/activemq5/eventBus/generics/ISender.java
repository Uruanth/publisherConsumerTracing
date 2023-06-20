package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics;

public interface ISender<I extends Event> {

    void sendEvent(final I event);
}
