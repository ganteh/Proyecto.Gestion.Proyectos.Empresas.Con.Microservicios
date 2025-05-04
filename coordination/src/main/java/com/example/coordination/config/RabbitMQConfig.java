package com.example.coordination.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "app.rabbitmq")
public class RabbitMQConfig {

    // Nombre de la exchange (tipo topic)
    public static final String PROJECT_EVENT_EXCHANGE = "project.events.exchange";

    // Nombre de la cola única para todos los eventos de estado
    public static final String PROJECT_EVENTS_QUEUE = "project.events.queue";

    // Routing keys
    public static final String PROJECT_APPROVED_ROUTING_KEY = "project.approved";
    public static final String PROJECT_REJECTED_ROUTING_KEY = "project.rejected";
    public static final String PROJECT_IN_EXECUTION_ROUTING_KEY = "project.in_execution";

    @Bean
    public TopicExchange projectExchange() {
        return new TopicExchange(PROJECT_EVENT_EXCHANGE);
    }

    @Bean
    public Queue projectEventsQueue() {
        return new Queue(PROJECT_EVENTS_QUEUE);
    }

    @Bean
    public Binding approvedBinding() {
        return BindingBuilder
                .bind(projectEventsQueue())
                .to(projectExchange())
                .with(PROJECT_APPROVED_ROUTING_KEY);
    }

    @Bean
    public Binding rejectedBinding() {
        return BindingBuilder
                .bind(projectEventsQueue())
                .to(projectExchange())
                .with(PROJECT_REJECTED_ROUTING_KEY);
    }

    @Bean
    public Binding inExecutionBinding() {
        return BindingBuilder
                .bind(projectEventsQueue())
                .to(projectExchange())
                .with(PROJECT_IN_EXECUTION_ROUTING_KEY);
    }


}
