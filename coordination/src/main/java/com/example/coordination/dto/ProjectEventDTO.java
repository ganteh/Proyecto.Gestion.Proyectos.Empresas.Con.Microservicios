package com.example.coordination.dto;



import com.example.coordination.entity.ProjectStateEnum;

public class ProjectEventDTO {
    private String projectId;
    private String projectName;
    private String companyNit;
    private String state;

    public ProjectEventDTO() {
    }

    public ProjectEventDTO(String projectId, String projectName, String companyNit, ProjectStateEnum state) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.companyNit = companyNit;
        this.state = state.name();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyNit() {
        return companyNit;
    }

    public void setCompanyNit(String companyNit) {
        this.companyNit = companyNit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}