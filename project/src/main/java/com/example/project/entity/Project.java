package com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
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

    private int maxDurationMonths;
    private int budget;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String companyNIT;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}

