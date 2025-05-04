package com.example.coordination.Test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.example.coordination.config.RabbitMQConfig.*;

@Component
public class TestRabbitMQSender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    public TestRabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String testMessage = "🧪 Mensaje de prueba - Proyecto aprobado";

        rabbitTemplate.convertAndSend(
                PROJECT_EVENT_EXCHANGE,
                PROJECT_APPROVED_ROUTING_KEY,
                testMessage
        );

        System.out.println("📤 Mensaje enviado a RabbitMQ.");
    }
}
