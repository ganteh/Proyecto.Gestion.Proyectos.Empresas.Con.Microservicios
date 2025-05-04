package com.example.coordination.service;

import com.example.coordination.entity.Project;
import com.example.coordination.entity.ProjectStateEnum;
import com.example.coordination.repository.ProjectRepository;
import com.example.coordination.state.AcceptedState;
import com.example.coordination.state.InExecutionState;
import com.example.coordination.state.ReceivedState;
import com.example.coordination.state.RejectedState;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> getById(String id) {
        return projectRepository.findById(id);
    }

    public Project approveProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));

        project.setState(ProjectStateEnum.ACCEPTED);
        Project saved = projectRepository.save(project);


        return saved;
    }

    public Project rejectProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
    
        project.setState(ProjectStateEnum.REJECTED);
    
        Project saved = projectRepository.save(project);

    
        return saved;
    }

    //state
    public void initState() {
        
        switch (state) {
            case RECEIVED -> this.stateInstance = new ReceivedState();
            case ACCEPTED -> this.stateInstance = new AcceptedState();
            case IN_EXECUTION -> this.stateInstance = new InExecutionState();
            case REJECTED -> this.stateInstance = new RejectedState();
            default -> throw new IllegalStateException("Estado desconocido: " + state);
        }
    }

    public void accept() {
        if (stateInstance == null) initState();
        stateInstance.accept(this);
    }

    public void reject() {
        if (stateInstance == null) initState();
        stateInstance.reject(this);
    }

    public void startExecution() {
        if (stateInstance == null) initState();
        stateInstance.startExecution(this);
    }

}