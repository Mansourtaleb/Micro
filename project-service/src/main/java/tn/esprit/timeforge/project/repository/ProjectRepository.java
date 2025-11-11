package tn.esprit.timeforge.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.timeforge.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}