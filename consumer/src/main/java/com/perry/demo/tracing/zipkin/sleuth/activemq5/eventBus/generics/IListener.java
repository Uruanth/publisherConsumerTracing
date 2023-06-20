package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.generics;

import javax.jms.JMSException;
import javax.jms.Message;

public interface IListener {

    String processEvent(final Message event) throws JMSException;
}
