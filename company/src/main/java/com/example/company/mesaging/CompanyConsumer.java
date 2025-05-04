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

    @RabbitListener(queues = "company-registration-queue")
    public void receiveCompany(CompanyEvent dto) {
        log.info("📥 Recibida empresa desde la cola: {}", dto);

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

    @RabbitListener(queues = "project-approval-queue")
    public void receiveApprovedProject(ProjectEvent dto) {
        log.info("📥 Recibido proyecto aprobado: {}", dto);

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
}
