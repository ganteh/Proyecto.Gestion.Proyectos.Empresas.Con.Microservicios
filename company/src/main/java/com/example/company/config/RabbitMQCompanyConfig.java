package com.example.company.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCompanyConfig {

    public static final String EXCHANGE = "company.exchange";

    // Empresa creada
    public static final String QUEUE_COMPANY_CREATED = "company.created.queue";
    public static final String ROUTING_KEY_COMPANY_CREATED = "company.created";

    // Proyecto aprobado
    public static final String QUEUE_PROJECT_APPROVED = "project.approved.queue";
    public static final String ROUTING_KEY_PROJECT_APPROVED = "project.approved";

    @Bean
    public DirectExchange companyExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue companyCreatedQueue() {
        return new Queue(QUEUE_COMPANY_CREATED, true);
    }

    @Bean
    public Queue projectApprovedQueue() {
        return new Queue(QUEUE_PROJECT_APPROVED, true);
    }

    @Bean
    public Binding bindingCompanyCreated(Queue companyCreatedQueue, DirectExchange companyExchange) {
        return BindingBuilder.bind(companyCreatedQueue)
                .to(companyExchange)
                .with(ROUTING_KEY_COMPANY_CREATED);
    }

    @Bean
    public Binding bindingProjectApproved(Queue projectApprovedQueue, DirectExchange companyExchange) {
        return BindingBuilder.bind(projectApprovedQueue)
                .to(companyExchange)
                .with(ROUTING_KEY_PROJECT_APPROVED);
    }
}


