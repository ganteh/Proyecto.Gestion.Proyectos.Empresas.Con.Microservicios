package com.example.company.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCompanyConfig {

    // ▶ Constantes para colas que ENVIAS (tú produces)
    public static final String PROJECT_EVENTS_EXCHANGE = "project.events.exchange";
    public static final String PROJECT_EVENTS_CRISTOBAL_QUEUE = "project.events.cristobal.queue";
    public static final String PROJECT_EVENTS_YEISON_QUEUE = "project.events.yeison.queue";

    // ▶ Constantes para colas que RECIBES (tú consumes)
    public static final String COMPANY_QUEUE = "company.queue"; // Desde Adrián
    public static final String PROJECT_EVENTS_QUEUE = "project.events.queue"; // Desde Cristóbal

    // ▶ Exchange tipo fanout para distribuir a múltiples colas
    @Bean
    public FanoutExchange projectEventsExchange() {
        return new FanoutExchange(PROJECT_EVENTS_EXCHANGE);
    }

    // ▶ Declaración de colas de destino
    @Bean
    public Queue projectEventsCristobalQueue() {
        return new Queue(PROJECT_EVENTS_CRISTOBAL_QUEUE, true);
    }

    @Bean
    public Queue projectEventsYeisonQueue() {
        return new Queue(PROJECT_EVENTS_YEISON_QUEUE, true);
    }

    // ▶ Bindings para conectar colas al exchange
    @Bean
    public Binding bindingCristobal(FanoutExchange projectEventsExchange, Queue projectEventsCristobalQueue) {
        return BindingBuilder.bind(projectEventsCristobalQueue).to(projectEventsExchange);
    }

    @Bean
    public Binding bindingYeison(FanoutExchange projectEventsExchange, Queue projectEventsYeisonQueue) {
        return BindingBuilder.bind(projectEventsYeisonQueue).to(projectEventsExchange);
    }

    // ▶ Declaración de colas que consumes
    @Bean
    public Queue companyQueue() {
        return new Queue(COMPANY_QUEUE, true);
    }

    @Bean
    public Queue projectEventsQueue() {
        return new Queue(PROJECT_EVENTS_QUEUE, true);
    }

    // ▶ Convertidor JSON para mensajes
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
