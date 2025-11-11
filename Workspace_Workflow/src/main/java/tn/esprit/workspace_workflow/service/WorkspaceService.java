package tn.esprit.workspace_workflow.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.workspace_workflow.entity.Workspace;
import tn.esprit.workspace_workflow.repository.WorkspaceRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkspaceService {

    private WorkspaceRepository workspaceRepository;


    public Workspace createWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }


    public Optional<Workspace> getWorkspaceById(String workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }


    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }


    public Workspace updateWorkspace(String workspaceId, Workspace updatedWorkspace) {
        return workspaceRepository.findById(workspaceId).map(existingWorkspace -> {
            if (updatedWorkspace.getWorkspaceName () != null) {
                existingWorkspace.setWorkspaceName (updatedWorkspace.getWorkspaceName ());
            }
            if (updatedWorkspace.getWorkspaceDescription () != null) {
                existingWorkspace.setWorkspaceDescription (updatedWorkspace.getWorkspaceDescription ());
            }
            if (updatedWorkspace.getWorkflows() != null) {
                existingWorkspace.setWorkflows(updatedWorkspace.getWorkflows());
            }
            return workspaceRepository.save(existingWorkspace);


        }).orElseThrow(() -> new RuntimeException("Espace de travail introuvable : " + workspaceId));
    }


    public void deleteWorkspace(String workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new RuntimeException("Espace de travail introuvable");
        }
        workspaceRepository.deleteById(workspaceId);
    }

}
