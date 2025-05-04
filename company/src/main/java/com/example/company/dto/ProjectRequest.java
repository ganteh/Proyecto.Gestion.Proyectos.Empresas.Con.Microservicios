package com.example.company.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {
    private String name;
    private String summary;
    private String objectives;
    private String description;
    private int maxMonths;
    private BigDecimal budget;
    private LocalDate startDate;   // Equivale a `date` en el monolito
    private String companyNIT;
}


