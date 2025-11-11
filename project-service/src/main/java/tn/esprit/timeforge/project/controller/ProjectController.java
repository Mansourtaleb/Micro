package tn.esprit.timeforge.project.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.timeforge.project.model.Project;
import tn.esprit.timeforge.project.service.ProjectService;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Project> getAll() {
        return service.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) {
        return service.getProjectById(id);
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return service.saveProject(project);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProject(id);
    }
}