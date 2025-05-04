package com.example.project.infra.dto;

import com.example.project.entity.ProjectStatus;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEvent {
    private UUID id;
    private String name;
    private String summary;
    private String objectives;
    private String description;
    private int maxDurationMonths;
    private int budget;
    private LocalDate startDate;
    private String companyNIT;
    private ProjectStatus status;
}
