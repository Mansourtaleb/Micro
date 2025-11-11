package tn.esprit.timeforge.project.service;

import org.springframework.stereotype.Service;
import tn.esprit.timeforge.project.model.Project;
import tn.esprit.timeforge.project.repository.ProjectRepository;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> getAllProjects() {
        return repository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Project saveProject(Project project) {
        return repository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        repository.deleteById(id);
    }
}