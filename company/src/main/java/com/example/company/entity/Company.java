package com.example.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(nullable = false)
    private String name;

    private String sector;
    private String contactPhone;
    private String contactFirstName;
    private String contactLastName;
    private String contactPosition;

    // Puedes agregar la relación si luego la necesitas:
    // @OneToMany(mappedBy = "companyNIT", cascade = CascadeType.ALL)
    // private List<Project> projects;
}


