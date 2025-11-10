package tn.esprit.workspace_workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.workspace_workflow.entity.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, String> {
}
