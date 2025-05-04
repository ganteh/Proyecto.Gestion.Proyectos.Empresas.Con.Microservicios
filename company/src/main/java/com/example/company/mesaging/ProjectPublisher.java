package com.example.company.mesaging;

import com.example.company.config.RabbitMQCompanyConfig;
import com.example.company.dto.ProjectEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishProjectApproved(ProjectEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQCompanyConfig.EXCHANGE,
                RabbitMQCompanyConfig.ROUTING_KEY_PROJECT_APPROVED,
                event
        );
    }
}
