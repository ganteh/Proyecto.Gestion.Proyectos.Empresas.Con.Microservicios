package com.example.company.service;


import com.example.company.dto.CompanyEvent;
import com.example.company.entity.Company;
import com.example.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public void saveFromEvent(CompanyEvent event) {
        // Si la empresa ya existe por NIT, no se vuelve a guardar
        if (repository.findByNit(event.getNit()).isPresent()) {
            return;
        }

        Company company = Company.builder()
                .nit(event.getNit())
                .name(event.getName())
                .sector(event.getSector())
                .contactPhone(event.getContactPhone())
                .contactFirstName(event.getContactFirstName())
                .contactLastName(event.getContactLastName())
                .contactPosition(event.getContactPosition())
                .build();

        repository.save(company);
    }
}

