package com.example.company.controller;


import com.example.company.dto.ProjectRequest;
import com.example.company.dto.ProjectResponse;
import com.example.company.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable UUID id, @RequestBody ProjectRequest request) {
        Optional<ProjectResponse> updated = projectService.update(id, request);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return projectService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> findAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("/company/{nit}")
    public ResponseEntity<List<ProjectResponse>> findByCompanyNIT(@PathVariable String nit) {
        return ResponseEntity.ok(projectService.findByCompanyNIT(nit));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ProjectResponse> approve(@PathVariable UUID id) {
        Optional<ProjectResponse> approved = projectService.approve(id);
        return approved.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
