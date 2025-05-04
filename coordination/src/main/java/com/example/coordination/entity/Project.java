package com.example.coordination.entity;
import com.example.coordination.repository.*;
import com.example.coordination.service.StudentService;
import com.example.coordination.state.*;
import jakarta.persistence.*;

@Entity
public class Project {

    StudentService studentService;
    StudentRepository StudentRepository;
    
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String companyNit;

    @Enumerated(EnumType.STRING)
    private ProjectStateEnum state;


    public Project() {}

    public Project(String id, String name, String companyNit) {

        this.id = id;
        this.name = name;
        this.companyNit = companyNit;
    }



    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyNit() {
        return companyNit;
    }

    public void setCompanyNit(String companyNit) {
        this.companyNit = companyNit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStateEnum getState() {
        return state;
    }

    public void setState(ProjectStateEnum state) {
        this.state = state;
    }

}