package com.example.project.infra.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    private String name;
    private String summary;
    private String objectives;
    private String description;
    private int maxDurationMonths;
    private int budget;
    private LocalDate startDate;
    private String companyNIT;
}

