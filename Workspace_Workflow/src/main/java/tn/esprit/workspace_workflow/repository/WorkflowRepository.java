package tn.esprit.workspace_workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.workspace_workflow.entity.Workflow;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, String> {
}