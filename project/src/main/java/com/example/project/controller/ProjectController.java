package com.example.project.controller;

import com.example.project.entity.Project;
import com.example.project.infra.dto.ProjectRequest;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        try {
            Project created = service.createProject(request);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el proyecto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable UUID id, @RequestBody ProjectRequest request) {
        try {
            Optional<Project> updated = service.updateProject(id, request);
            return updated
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable UUID id) {
        try {
            boolean deleted = service.deleteProject(id);
            return deleted ? ResponseEntity.ok("Proyecto eliminado") :
                    ResponseEntity.status(404).body("Proyecto no encontrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se puede eliminar: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(service.getAllProjects());
    }

    @GetMapping("/by-nit/{nit}")
    public ResponseEntity<List<Project>> getProjectsByCompanyNIT(@PathVariable String nit) {
        return ResponseEntity.ok(service.getProjectsByCompanyNIT(nit));
    }
}
