package com.example.company.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private Integer maxMonths;
    private BigDecimal budget;
    private LocalDate startDate;
    private String status; // por ejemplo: "APPROVED"
    private String companyNIT;
}

