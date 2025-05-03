package com.example.company.infra.dto;

//esto no es un DTO PERO YA LO MUEVO DE CARPETA
import com.example.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEvent {
    private String eventType;
    private Company company;
    private LocalDateTime timestamp = LocalDateTime.now();
}