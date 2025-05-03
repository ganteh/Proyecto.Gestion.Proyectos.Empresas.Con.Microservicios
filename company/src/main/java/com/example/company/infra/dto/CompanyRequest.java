package com.example.company.infra.dto;

import lombok.Data;

@Data
public class CompanyRequest {
    private String nit;
    private String name;
    private String sector;
    private String contactPhone;
    private String contactFirstName;
    private String contactLastName;
    private String contactPosition;

}