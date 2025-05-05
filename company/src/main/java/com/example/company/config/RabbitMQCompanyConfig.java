package com.example.company.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCompanyConfig {

    public static final String PROJECT_EVENTS_EXCHANGE = "project.events.exchange";
    public static final String PROJECT_EVENTS_CRISTOBAL = "project.events.cristobal.queue";
    public static final String PROJECT_EVENTS_YEISON = "project.events.yeison.queue";

    @Bean
    public FanoutExchange projectEventsExchange() {
        return new FanoutExchange(PROJECT_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue projectEventsCristobalQueue() {
        return new Queue(PROJECT_EVENTS_CRISTOBAL, true);
    }

    @Bean
    public Queue projectEventsYeisonQueue() {
        return new Queue(PROJECT_EVENTS_YEISON, true);
    }

    @Bean
    public Binding bindingCristobal(FanoutExchange projectEventsExchange, Queue projectEventsCristobalQueue) {
        return BindingBuilder.bind(projectEventsCristobalQueue).to(projectEventsExchange);
    }

    @Bean
    public Binding bindingYeison(FanoutExchange projectEventsExchange, Queue projectEventsYeisonQueue) {
        return BindingBuilder.bind(projectEventsYeisonQueue).to(projectEventsExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
