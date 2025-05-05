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

    /**
     *  Publica un proyecto aprobado al exchange para Yeison y Cristóbal
     */
    public void publishApprovedProject(ProjectEvent projectEvent) {
        log.info("📤 Enviando proyecto aprobado a exchange: {}", projectEvent);
        rabbitTemplate.convertAndSend(
                RabbitMQCompanyConfig.PROJECT_EVENTS_EXCHANGE,
                "", // FanoutExchange ignora la routingKey
                projectEvent
        );
    }
}


