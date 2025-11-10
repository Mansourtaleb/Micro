package tn.esprit.workspace_workflow.service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.workspace_workflow.client.User;
import tn.esprit.workspace_workflow.entity.Workflow;
import tn.esprit.workspace_workflow.repository.WorkflowRepository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public Workflow assignCollaborators(String workflowId, List<User> collaborators) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        if (collaborators == null || collaborators.isEmpty()) {
            throw new IllegalArgumentException("The list of collaborators cannot be empty.");
        }

        // Map collaborators to a stable identifier. Using email as identifier for now.
        List<String> collaboratorIds = collaborators.stream()
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        workflow.setCollaboratorIds(collaboratorIds);
        log.info("Collaborators assigned to workflow: {}", workflowId);
        return workflowRepository.save(workflow);
    }

    public String trackWorkflow(String workflowId) {

        boolean exists = workflowRepository.existsById(workflowId);
        return exists ? "Valide" : "Non Valide";
    }


    public Map<String, Object> getDashboardStats() {

        List<Workflow> workflows = workflowRepository.findAll();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        // Statistiques mensuelles : nombre de workflows par mois
        Map<String, Long> workflowsPerMonth = workflows.stream()
                .collect(Collectors.groupingBy(
                        wf -> sdf.format(wf.getStartDate()),
                        Collectors.counting()
                ));

        // Moyenne des étapes par mois
        Map<String, Double> averageStepsPerMonth = workflows.stream()
                .collect(Collectors.groupingBy(
                        wf -> sdf.format(wf.getStartDate()),
                        Collectors.averagingInt(wf -> wf.getSteps().size())
                ));

        // Répartition des workflows par taille (nombre d’étapes)
        Map<String, Long> workflowsBySize = workflows.stream()
                .collect(Collectors.groupingBy(
                        wf -> {
                            int size = wf.getSteps().size();
                            if (size <= 3) return "Petit (<=3)";
                            else if (size <= 6) return "Moyen (4-6)";
                            else return "Grand (>6)";
                        },
                        Collectors.counting()
                ));

        // Compilation des stats
        Map<String, Object> stats = new HashMap<>();
        stats.put("workflowsPerMonth", workflowsPerMonth);
        stats.put("averageStepsPerMonth", averageStepsPerMonth);
        stats.put("workflowsBySize", workflowsBySize);
        stats.put("totalWorkflows", workflows.size());

        return stats;
    }

}
