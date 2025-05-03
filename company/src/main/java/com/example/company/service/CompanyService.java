package com.example.company.service;

import com.example.company.infra.dto.CompanyEvent;
import com.example.company.infra.dto.CompanyRequest;
import com.example.company.entity.Company;
import com.example.company.exception.CompanyNotFoundException;
import com.example.company.exception.DuplicateNitException;
import com.example.company.repository.CompanyRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RabbitTemplate rabbitTemplate;

    private static final String COMPANY_EXCHANGE = "company.exchange";
    private static final String ROUTING_KEY = "company.event";

    @Autowired
    public CompanyService(CompanyRepository companyRepository, RabbitTemplate rabbitTemplate) {
        this.companyRepository = companyRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Company createCompany(CompanyRequest request) {
        if (companyRepository.existsByNit(request.getNit())) {
            throw new DuplicateNitException("NIT " + request.getNit() + " ya está registrado");
        }

        Company company = mapToEntity(request);
        Company savedCompany = companyRepository.save(company);
        publishEvent("CREATED", savedCompany);
        return savedCompany;
    }

    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Company getCompanyByNit(String nit) {
        return companyRepository.findByNit(nit)
                .orElseThrow(() -> new CompanyNotFoundException("Empresa no encontrada con NIT: " + nit));
    }

    @Transactional
    public Company updateCompany(String nit, CompanyRequest request) {
        Company existingCompany = getCompanyByNit(nit);

        if (!existingCompany.getNit().equals(request.getNit()) &&
                companyRepository.existsByNit(request.getNit())) {
            throw new DuplicateNitException("NIT " + request.getNit() + " ya está en uso");
        }

        updateEntity(existingCompany, request);
        Company updatedCompany = companyRepository.save(existingCompany);
        publishEvent("UPDATED", updatedCompany);
        return updatedCompany;
    }

    @Transactional
    public void deleteCompany(String nit) {
        Company company = getCompanyByNit(nit);
        companyRepository.delete(company);
        publishEvent("DELETED", company);
    }

    private Company mapToEntity(CompanyRequest request) {
        return Company.builder()
                .nit(request.getNit())
                .name(request.getName())
                .sector(request.getSector())
                .contactPhone(request.getContactPhone())
                .contactFirstName(request.getContactFirstName())
                .contactLastName(request.getContactLastName())
                .contactPosition(request.getContactPosition())
                .build();
    }

    private void updateEntity(Company company, CompanyRequest request) {
        company.setNit(request.getNit());
        company.setName(request.getName());
        company.setSector(request.getSector());
        company.setContactPhone(request.getContactPhone());
        company.setContactFirstName(request.getContactFirstName());
        company.setContactLastName(request.getContactLastName());
        company.setContactPosition(request.getContactPosition());
    }

    private void publishEvent(String eventType, Company company) {
        rabbitTemplate.convertAndSend(
                COMPANY_EXCHANGE,
                ROUTING_KEY,
                new CompanyEvent(eventType, company)
        );
    }
}