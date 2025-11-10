package tn.esprit.workspace_workflow.entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workflows")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workflow {
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "workflowName cannot be null")
    private String workflowName;

    @NotNull(message = "Steps cannot be null")
    @ElementCollection
    private List<String> steps = new ArrayList<>();

    @NotNull(message = "FileName cannot be null")
    private String fileName;

    @NotNull(message = "startDate cannot be null")
    private Date startDate;

    @NotNull(message = "endDate cannot be null")
    private Date endDate;

    // Store external user IDs instead of DBRef users
    @ElementCollection
    private List<String> collaboratorIds = new ArrayList<>();

    private String creatorId;
}
