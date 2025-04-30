package com.example.coordination.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.coordination.model.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, String> {
    List<Proyecto> findByEstado(String estado);
}
