package com.example.project.repository;

import com.example.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByCompanyNIT(String companyNIT);
}
