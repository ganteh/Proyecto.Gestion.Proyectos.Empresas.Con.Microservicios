package com.example.coordination.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.coordination.entity.Project;

import io.micrometer.common.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

   
    // Elimina un proyecto por su ID
    @Override
    void deleteById(String projectId);

    // Encuentra todos los proyectos asociados a un NIT de empresa
    @Query("SELECT p FROM Project p WHERE p.companyNit = :nit")
    List<Project> findByCompanyNit(String companyNit);

    // Encuentra proyectos por estado
    List<Project> findByState(String state);

    // Soporte para búsquedas por nombre (opcional)
    List<Project> findByNameContainingIgnoreCase(String name);

}
