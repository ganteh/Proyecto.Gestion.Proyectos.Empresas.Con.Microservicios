package com.example.coordination.messaging;

import com.example.coordination.config.RabbitMQConfig;
import com.example.coordination.dto.ProjectEventDTO;
import com.example.coordination.entity.ProjectStateEnum;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProjectEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ProjectEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendProjectStateChange(String projectId, String ProjectName, String CompanyNIT, ProjectStateEnum newState ) {
        ProjectEventDTO event = new ProjectEventDTO(
                projectId,
                ProjectName,
                CompanyNIT,
                newState
        );

        String routingKey = switch (newState.name().toUpperCase()) {
            case "APPROVED" -> RabbitMQConfig.PROJECT_APPROVED_ROUTING_KEY;
            case "REJECTED" -> RabbitMQConfig.PROJECT_REJECTED_ROUTING_KEY;
            case "IN_EXECUTION" -> RabbitMQConfig.PROJECT_IN_EXECUTION_ROUTING_KEY;
            default -> throw new IllegalArgumentException("Invalid state: " + newState);
        };

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PROJECT_EVENT_EXCHANGE,
                routingKey,
                event
        );

        System.out.println("Mensaje enviado a RabbitMQ: " + event);
    }

}