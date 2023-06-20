package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;

@Configuration
public class JmsConfig {

    @Value("${spring.artemis.broker-url}")
    String BROKER_URL;

    @Value("${spring.artemis.user}")
    String BROKER_USERNAME;

    @Value("${spring.artemis.password}")
    String BROKER_PASSWORD;

    @Value("${app.bus.concurrency.min}")
    String MIN_CONCURRENCY;

    @Value("${app.bus.concurrency.max}")
    String MAX_CONCURRENCY;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        //TODO Politica de reintentos
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        redeliveryPolicy.setRedeliveryDelay(5000);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setUserName(BROKER_USERNAME);
        connectionFactory.setPassword(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        /**
         * TODO Optional Con esto todos los subscritos reciben una copia del mensaje
         * Si no se coloca, solo se entrega el mensaje a un subscriptor
         */
//        template.setPubSubDomain(true);
        template.setConnectionFactory(connectionFactory());
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        /**
         * TODO Optional Con esto todos los subscritos reciben una copia del mensaje
         * Si no se coloca, solo se entrega el mensaje a un subscriptor
         */
//        factory.setPubSubDomain(true);
        factory.setConcurrency(new StringBuilder()
                .append(MIN_CONCURRENCY)
                .append("-")
                .append(MAX_CONCURRENCY)
                .toString());
        return factory;
    }

}