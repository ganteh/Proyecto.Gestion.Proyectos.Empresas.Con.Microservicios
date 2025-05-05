package com.example.company.mesaging;

import com.example.company.dto.CompanyEvent;
import com.example.company.dto.ProjectEvent;
import com.example.company.entity.Company;
import com.example.company.entity.Project;
import com.example.company.entity.ProjectStatus;
import com.example.company.repository.CompanyRepository;
import com.example.company.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyConsumer {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;

    /**
     *  Escucha los proyectos enviados por Cristóbal desde project.events.queue
     */
    @RabbitListener(queues = "project.events.queue")
    public void receiveProjectFromCristobal(ProjectEvent dto) {
        log.info("📥 Proyecto recibido desde Cristóbal: {}", dto);

        Project project = Project.builder()
                .id(dto.getId())
                .name(dto.getName())
                .summary(dto.getSummary())
                .objectives(dto.getObjectives())
                .description(dto.getDescription())
                .maxMonths(dto.getMaxMonths())
                .budget(dto.getBudget())
                .startDate(dto.getStartDate())
                .companyNIT(dto.getCompanyNIT())
                .status(ProjectStatus.valueOf(dto.getStatus()))
                .build();

        projectRepository.save(project);
    }

    /**
     *  Escucha las empresas nuevas desde Adrián (login) por company.queue
     */
    @RabbitListener(queues = "company.queue")
    public void receiveCompanyFromLogin(CompanyEvent dto) {
        log.info("📥 Empresa recibida desde login (Adrián): {}", dto);

        Company company = Company.builder()
                .nit(dto.getNit())
                .name(dto.getName())
                .sector(dto.getSector())
                .contactPhone(dto.getContactPhone())
                .contactFirstName(dto.getContactFirstName())
                .contactLastName(dto.getContactLastName())
                .contactPosition(dto.getContactPosition())
                .build();

        companyRepository.save(company);
    }
}
