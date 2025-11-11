package tn.esprit.timeforge.project.service;

import tn.esprit.timeforge.project.model.Project;
import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project saveProject(Project project);
    void deleteProject(Long id);
}