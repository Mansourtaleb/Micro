package tn.esprit.workspace_workflow.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.workspace_workflow.FullWorkspaceResponse;
import tn.esprit.workspace_workflow.entity.Workspace;
import tn.esprit.workspace_workflow.service.WorkspaceService;
import tn.esprit.workspace_workflow.service.TwilioSmsService;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final TwilioSmsService twilioSmsService;

    @PostMapping("/create")
    public ResponseEntity<Workspace> createWorkspace(@RequestBody Workspace workspace) {
        System.out.println("WorkspaceName: " + workspace.getWorkspaceName() +
                " WorkspaceDescription: " + workspace.getWorkspaceDescription());

        if (workspace.getWorkspaceName() == null || workspace.getWorkspaceDescription() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Workspace savedWorkspace = workspaceService.createWorkspace(workspace);

        // Envoi d'un SMS lors de la création d'un espace de travail
        twilioSmsService.sendSms("+21694415244", "Un nouvel espace de travail a été créé : " + savedWorkspace.getWorkspaceName());

        return ResponseEntity.ok(savedWorkspace);
    }

    @GetMapping("/getWorkspaceById/{workspaceId}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable String workspaceId) {
        return workspaceService.getWorkspaceById(workspaceId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllWorkspaces")
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces());
    }

    @PutMapping("/update/{workspaceId}")
    public ResponseEntity<?> updateWorkspace(@PathVariable String workspaceId,
                                             @RequestBody Workspace workspace) {
        try {
            Workspace updatedWorkspace = workspaceService.updateWorkspace(workspaceId, workspace);

            // Envoi d'un SMS lors de la mise à jour de l'espace de travail
            twilioSmsService.sendSms("+21694415244", "L'espace de travail a été mis à jour : " + updatedWorkspace.getWorkspaceName());

            return ResponseEntity.ok(updatedWorkspace);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Espace de travail introuvable: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable String workspaceId) {
        try {
            workspaceService.deleteWorkspace(workspaceId);

            // Envoi d'un SMS lors de la suppression de l'espace de travail
            //twilioSmsService.sendSms("+21694415244", "Un espace de travail a été supprimé avec l'ID : " + workspaceId);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/withUsers/{workspaceId}")
    public ResponseEntity<FullWorkspaceResponse> findWorkspaceWithUsers(@PathVariable String workspaceId) {
        try {
            return ResponseEntity.ok(workspaceService.findWorkspaceWithUsers(workspaceId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
