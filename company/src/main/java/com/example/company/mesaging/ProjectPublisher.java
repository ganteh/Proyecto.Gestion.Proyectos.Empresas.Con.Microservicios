package com.example.company.mesaging;


import com.example.company.config.RabbitMQCompanyConfig;
import com.example.company.dto.ProjectEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishProjectApproved(ProjectEvent event) {
        log.info("📤 Enviando proyecto aprobado a la exchange: {}", event);

        rabbitTemplate.convertAndSend(
                RabbitMQCompanyConfig.PROJECT_EVENTS_EXCHANGE, // exchange tipo fanout
                "", // sin routing key, porque fanout lo ignora
                event
        );
    }
}

