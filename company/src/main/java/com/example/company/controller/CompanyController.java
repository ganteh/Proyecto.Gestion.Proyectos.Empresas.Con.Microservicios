package com.example.company.controller;


import com.example.company.infra.dto.CompanyRequest;
import com.example.company.entity.Company;
import com.example.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(request));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<Company> getCompanyByNit(@PathVariable String nit) {
        return ResponseEntity.ok(companyService.getCompanyByNit(nit));
    }

    @PutMapping("/{nit}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable String nit,
            @RequestBody CompanyRequest request
    ) {
        return ResponseEntity.ok(companyService.updateCompany(nit, request));
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<Void> deleteCompany(@PathVariable String nit) {
        companyService.deleteCompany(nit);
        return ResponseEntity.noContent().build();
    }
}