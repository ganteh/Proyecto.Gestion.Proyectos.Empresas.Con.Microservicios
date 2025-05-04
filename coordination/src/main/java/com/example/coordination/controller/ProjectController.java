package com.example.coordination.controller;

import com.example.coordination.entity.Project;
import com.example.coordination.entity.ProjectStateEnum;
import com.example.coordination.repository.ProjectRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveProject(@PathVariable String id) {
        Optional<Project> optionalProject = projectRepository.findById(id);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            if (project.getState() == ProjectStateEnum.RECEIVED) {
                project.setState(ProjectStateEnum.ACCEPTED);
                projectRepository.save(project);
                return ResponseEntity.ok("Proyecto aprobado correctamente.");
            } else {
                return ResponseEntity.badRequest().body("El proyecto no se encuentra en estado RECEIVED.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}