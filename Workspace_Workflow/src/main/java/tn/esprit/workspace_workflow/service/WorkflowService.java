package tn.esprit.workspace_workflow.service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.workspace_workflow.entity.Workflow;
import tn.esprit.workspace_workflow.repository.WorkflowRepository;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class WorkflowService {

    private WorkflowRepository workflowRepository;


    public Workflow createWorkflow(Workflow workflow) {
        if (workflow.getId() == null || workflow.getId().isEmpty()) {
            workflow.setId( UUID.randomUUID().toString());
        }
        return workflowRepository.save(workflow);
    }


    public Optional<Workflow> getWorkflowById(String workflowId) {
        return workflowRepository.findById(workflowId);
    }


    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }


    public Workflow updateWorkflow(String workflowId, Workflow updatedWorkflow) {
        return workflowRepository.findById(workflowId)
                .map(existingWorkflow -> {
                    if (updatedWorkflow.getWorkflowName() != null) {
                        existingWorkflow.setWorkflowName(updatedWorkflow.getWorkflowName().trim());
                    }
                    if (updatedWorkflow.getSteps() != null) {
                        existingWorkflow.setSteps(updatedWorkflow.getSteps());
                    }
                    if (updatedWorkflow.getCollaboratorIds() != null) {
                        existingWorkflow.setCollaboratorIds(updatedWorkflow.getCollaboratorIds());
                    }
                    if (updatedWorkflow.getCreatorId() != null) {
                        existingWorkflow.setCreatorId(updatedWorkflow.getCreatorId());
                    }

                    if (updatedWorkflow.getFileName () != null) {
                        existingWorkflow.setFileName(updatedWorkflow.getFileName());
                    }

                    if (updatedWorkflow.getStartDate () != null) {
                        existingWorkflow.setStartDate(updatedWorkflow.getStartDate());
                    }
                    if (updatedWorkflow.getEndDate () != null) {
                        existingWorkflow.setEndDate(updatedWorkflow.getEndDate());
                    }

                    log.info("Workflow mis à jour : {}", existingWorkflow);
                    return workflowRepository.save(existingWorkflow);
                })
                .orElseThrow(() -> new RuntimeException("Workflow non trouvé : " + workflowId));
    }


    public void deleteWorkflow(String workflowId) {
        if (workflowRepository.existsById(workflowId)) {
            workflowRepository.deleteById(workflowId);
            log.info("Workflow supprimé : {}", workflowId);
        } else {
            throw new RuntimeException("Workflow introuvable : " + workflowId);
        }
    }

}
