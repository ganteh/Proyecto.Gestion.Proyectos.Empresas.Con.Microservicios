package com.example.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String objectives;

    @Column(nullable = false)
    private String description;

    private int maxMonths;

    @Column(nullable = false)
    private BigDecimal budget;

    /**
     * Fecha de creación del proyecto (equivalente a `date` en el monolito).
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * NIT de la empresa asociada (equivalente a `company` en el monolito).
     */
    @Column(nullable = false)
    private String companyNIT;

    /**
     * Estado del proyecto: RECEIVED, REJECTED, IN_EXECUTION, CLOSED.
     */
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}
