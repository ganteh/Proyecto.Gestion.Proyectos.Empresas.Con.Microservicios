package com.example.project.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "project.queue";
    public static final String EXCHANGE = "project.exchange";
    public static final String ROUTING_KEY = "project.routingKey";

    @Bean
    public Queue projectQueue() {
        return new Queue(QUEUE, false);
    }

    @Bean
    public DirectExchange projectExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue projectQueue, DirectExchange projectExchange) {
        return BindingBuilder.bind(projectQueue).to(projectExchange).with(ROUTING_KEY);
    }
}
