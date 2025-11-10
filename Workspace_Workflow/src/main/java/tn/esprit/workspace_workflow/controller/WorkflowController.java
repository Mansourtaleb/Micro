package tn.esprit.workspace_workflow.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.workspace_workflow.entity.Workflow;
import tn.esprit.workspace_workflow.service.FileService;
import tn.esprit.workspace_workflow.service.WorkflowService;
import tn.esprit.workspace_workflow.service.TwilioSmsService;
import tn.esprit.workspace_workflow.client.User;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workflows")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j

public class WorkflowController {

    private final WorkflowService workflowService;
    private final TwilioSmsService twilioSmsService;
    private final FileService fileService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Workflow> createWorkflow(
            @RequestParam("workflowName") String workflowName,
            @RequestParam("steps") List<String> steps,
            @RequestParam("file") MultipartFile file,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        try {
            Workflow workflow = new Workflow();
            workflow.setWorkflowName(workflowName);
            workflow.setSteps(steps);
            workflow.setFileName(file.getOriginalFilename());  // On peut aussi stocker le fichier
            workflow.setStartDate(startDate);
            workflow.setEndDate(endDate);

            Workflow createdWorkflow = workflowService.createWorkflow(workflow);

            // Envoi SMS
            twilioSmsService.sendSms("+21694415244", "Un nouveau workflow a été créé : " + createdWorkflow.getWorkflowName());

            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkflow);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @GetMapping("/getWorkflowById/{workflowId}")
    public ResponseEntity<Workflow> getWorkflowById(@PathVariable String workflowId) {
        return workflowService.getWorkflowById(workflowId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllWorkflows")
    public ResponseEntity<List<Workflow>> getAllWorkflows() {
        return ResponseEntity.ok(workflowService.getAllWorkflows());
    }

    @PutMapping(value = "/update/{workflowId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateWorkflow(
            @PathVariable String workflowId,
            @RequestParam("workflowName") String workflowName,
            @RequestParam("steps") List<String> steps,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        try {
            // Créer l'objet Workflow
            Workflow workflow = new Workflow();
            workflow.setWorkflowName(workflowName);
            workflow.setSteps(steps);

            // Si un fichier est envoyé, on le gère
            if (file != null && !file.isEmpty()) {
                workflow.setFileName(file.getOriginalFilename()); // ou tu peux choisir de le stocker
            }

            workflow.setStartDate(startDate);
            workflow.setEndDate(endDate);

            // Appel du service pour mettre à jour le workflow
            Workflow updatedWorkflow = workflowService.updateWorkflow(workflowId, workflow);

            // Envoi de l'alerte SMS
            twilioSmsService.sendSms("+21694415244", "Le workflow a été mis à jour : " + updatedWorkflow.getWorkflowName());

            return ResponseEntity.ok(updatedWorkflow);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Workflow not found with ID: " + workflowId);  // Detailed error message
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the workflow: " + e.getMessage());
        }
    }




    @DeleteMapping("/delete/{workflowId}")
    public ResponseEntity<Void> deleteWorkflow(@PathVariable String workflowId) {
        try {
            workflowService.deleteWorkflow(workflowId);

            // Envoi d'un SMS lors de la suppression du workflow
            twilioSmsService.sendSms("+21694415244", "Un workflow a été supprimé avec l'ID : " + workflowId);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Track/{workflowId}")
    public String trackWorkflow(@PathVariable String workflowId) {
        return workflowService.trackWorkflow(workflowId);
    }

    @PutMapping("/assign-collaborators/{workflowId}")
    public ResponseEntity<Workflow> assignCollaborators(@PathVariable String workflowId, @RequestBody List<User> collaborators) {
        Workflow updatedWorkflow = workflowService.assignCollaborators(workflowId, collaborators);
        return ResponseEntity.ok(updatedWorkflow);
    }
    // Méthodes pour la gestion des fichiers (issues du FileController)
    @PostMapping("/{workflowId}/files/upload")
    public ResponseEntity<Object> uploadFile(@PathVariable String workflowId,
                                             @RequestParam MultipartFile file) {
        try {
            fileService.save(file);
            log.info("File saved for workflow {}", workflowId);

            // Return a structured JSON response
            return ResponseEntity.ok().body(new FileUploadResponse("File uploaded successfully", workflowId));
        } catch (Exception e) {
            log.error("Error uploading file for workflow {}: {}", workflowId, e.getMessage());
            return ResponseEntity.internalServerError().body("Error uploading file");
        }
    }

    @GetMapping("/{workflowId}/files/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String workflowId,
                                            @PathVariable String fileName) {
        try {
            Resource resource = fileService.getFile(fileName);
            if (resource != null) {
                return ResponseEntity.ok()
                        .header( HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType( MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving file {} for workflow {}: {}", fileName, workflowId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = workflowService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }


}
