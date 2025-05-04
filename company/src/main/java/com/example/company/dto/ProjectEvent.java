package com.example.company.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ProjectEvent {
    private UUID id;
    private String name;
    private String summary;
    private String objectives;
    private String description;
    private int maxMonths;
    private BigDecimal budget;
    private LocalDate startDate;
    private String companyNIT;
    private String status;
}
