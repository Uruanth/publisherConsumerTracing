package com.perry.demo.tracing.zipkin.sleuth.activemq5.eventBus.config;

//import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
public class JmsConfig {

    @Value("${spring.artemis.broker-url}")
    String BROKER_URL;
    @Value("${spring.artemis.user}")
    String BROKER_USERNAME;
    @Value("${spring.artemis.password}")
    String BROKER_PASSWORD;

    @Bean("jmsConnectionFactory")
    public ActiveMQConnectionFactory jmsConnectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        //TODO ActiveMQ 5
        connectionFactory.setUserName(BROKER_USERNAME);
//        TODO Artemis
//        connectionFactory.setUser(BROKER_USERNAME);
        connectionFactory.setPassword(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
//        template.setPubSubDomain(true);
        template.setConnectionFactory(jmsConnectionFactory());
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory) throws JMSException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1-1");
        return factory;
    }

}