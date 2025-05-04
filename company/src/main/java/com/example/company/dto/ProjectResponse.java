package com.example.company.dto;

import com.example.company.entity.ProjectStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private UUID id;
    private String name;
    private String summary;
    private String objectives;
    private String description;
    private int maxMonths;
    private BigDecimal budget;
    private LocalDate startDate;
    private String companyNIT;
    private ProjectStatus status;
}
