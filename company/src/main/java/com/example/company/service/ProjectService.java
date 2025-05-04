package com.example.company.service;

import com.example.company.dto.ProjectRequest;
import com.example.company.dto.ProjectResponse;
import com.example.company.dto.ProjectEvent;
import com.example.company.entity.Project;
import com.example.company.entity.ProjectStatus;
import com.example.company.mesaging.ProjectPublisher;
import com.example.company.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;
    private final ProjectPublisher publisher;

    public ProjectResponse create(ProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .summary(request.getSummary())
                .objectives(request.getObjectives())
                .description(request.getDescription())
                .maxMonths(request.getMaxMonths())
                .budget(request.getBudget())
                .startDate(request.getStartDate())
                .companyNIT(request.getCompanyNIT())
                .status(ProjectStatus.RECEIVED)
                .build();

        Project saved = repository.save(project);
        return toResponse(saved);
    }

    public Optional<ProjectResponse> update(UUID id, ProjectRequest request) {
        return repository.findById(id).map(existing -> {
            if (existing.getStatus() != ProjectStatus.RECEIVED) {
                throw new IllegalStateException("Solo se pueden editar proyectos en estado RECEIVED");
            }

            existing.setName(request.getName());
            existing.setSummary(request.getSummary());
            existing.setObjectives(request.getObjectives());
            existing.setDescription(request.getDescription());
            existing.setMaxMonths(request.getMaxMonths());
            existing.setBudget(request.getBudget());
            existing.setStartDate(request.getStartDate());

            return toResponse(repository.save(existing));
        });
    }

    public boolean delete(UUID id) {
        return repository.findById(id).map(project -> {
            if (project.getStatus() != ProjectStatus.RECEIVED) {
                throw new IllegalStateException("Solo se pueden eliminar proyectos en estado RECEIVED");
            }
            repository.delete(project);
            return true;
        }).orElse(false);
    }

    public List<ProjectResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> findByCompanyNIT(String nit) {
        return repository.findByCompanyNIT(nit).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProjectResponse> approve(UUID id) {
        return repository.findById(id).map(project -> {
            if (project.getStatus() != ProjectStatus.RECEIVED) {
                throw new IllegalStateException("Solo se pueden aprobar proyectos en estado RECEIVED");
            }

            project.setStatus(ProjectStatus.IN_EXECUTION); // o el estado que uses como aprobado

            Project saved = repository.save(project);

            // Crear evento y enviarlo a la cola
            ProjectEvent event = new ProjectEvent();
            event.setId(saved.getId());
            event.setName(saved.getName());
            event.setSummary(saved.getSummary());
            event.setObjectives(saved.getObjectives());
            event.setDescription(saved.getDescription());
            event.setMaxMonths(saved.getMaxMonths());
            event.setBudget(saved.getBudget());
            event.setStartDate(saved.getStartDate());
            event.setCompanyNIT(saved.getCompanyNIT());
            event.setStatus(saved.getStatus().name());

            publisher.publishProjectApproved(event);

            return toResponse(saved);
        });
    }

    // Metodo auxiliar
    private ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .summary(project.getSummary())
                .objectives(project.getObjectives())
                .description(project.getDescription())
                .maxMonths(project.getMaxMonths())
                .budget(project.getBudget())
                .startDate(project.getStartDate())
                .companyNIT(project.getCompanyNIT())
                .status(project.getStatus())
                .build();
    }
}
