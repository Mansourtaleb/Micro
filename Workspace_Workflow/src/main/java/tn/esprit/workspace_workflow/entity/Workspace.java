package tn.esprit.workspace_workflow.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workspaces")
public class Workspace {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "Workspace_name cannot be null")
    @JsonProperty("workspaceName")
    private String workspaceName;

    @NotNull(message = "Workspace_description cannot be null")
    @JsonProperty("workspaceDescription")
    private String workspaceDescription;

    // Association to workflows via join table to avoid owning side in Workflow
    @OneToMany
    @JoinTable(
            name = "workspace_workflows",
            joinColumns = @JoinColumn(name = "workspace_id"),
            inverseJoinColumns = @JoinColumn(name = "workflow_id")
    )
    private List<Workflow> Workflows;


    public Workspace(String workspaceName, String workspaceDescription) {
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
    }

}
