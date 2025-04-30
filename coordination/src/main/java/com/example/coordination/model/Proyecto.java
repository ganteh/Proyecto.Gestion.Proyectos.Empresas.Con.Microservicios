package com.example.coordination.model;
import jakarta.persistence.*;

@Entity
public class Proyecto {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String estado;
    private Long idEmpresa;
}