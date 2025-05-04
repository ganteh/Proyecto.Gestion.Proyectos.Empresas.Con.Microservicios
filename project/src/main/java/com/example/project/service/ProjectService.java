package com.example.project.service;


import com.example.project.entity.Project;
import com.example.project.entity.ProjectStatus;
import com.example.project.infra.dto.ProjectRequest;
import com.example.project.infra.dto.ProjectEvent;
import com.example.project.repository.ProjectRepository;
import com.example.project.infra.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public Project createProject(ProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .summary(request.getSummary())
                .objectives(request.getObjectives())
                .description(request.getDescription())
                .maxDurationMonths(request.getMaxDurationMonths())
                .budget(request.getBudget())
                .startDate(request.getStartDate())
                .companyNIT(request.getCompanyNIT())
                .status(ProjectStatus.PENDIENTE)
                .build();

        Project saved = repository.save(project);

        ProjectEvent event = ProjectEvent.builder()
                .id(saved.getId())
                .name(saved.getName())
                .summary(saved.getSummary())
                .objectives(saved.getObjectives())
                .description(saved.getDescription())
                .maxDurationMonths(saved.getMaxDurationMonths())
                .budget(saved.getBudget())
                .startDate(saved.getStartDate())
                .companyNIT(saved.getCompanyNIT())
                .status(saved.getStatus())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );

        return saved;
    }

    public Optional<Project> updateProject(UUID id, ProjectRequest request) {
        return repository.findById(id).map(existing -> {
            if (existing.getStatus() != ProjectStatus.PENDIENTE) {
                throw new IllegalStateException("Solo se pueden editar proyectos en estado PENDIENTE");
            }

            existing.setName(request.getName());
            existing.setSummary(request.getSummary());
            existing.setObjectives(request.getObjectives());
            existing.setDescription(request.getDescription());
            existing.setMaxDurationMonths(request.getMaxDurationMonths());
            existing.setBudget(request.getBudget());
            existing.setStartDate(request.getStartDate());

            return repository.save(existing);
        });
    }

    public boolean deleteProject(UUID id) {
        return repository.findById(id).map(project -> {
            if (project.getStatus() != ProjectStatus.PENDIENTE) {
                throw new IllegalStateException("Solo se pueden eliminar proyectos en estado PENDIENTE");
            }

            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    public List<Project> getProjectsByCompanyNIT(String nit) {
        return repository.findByCompanyNIT(nit);
    }
}
