package tn.esprit.workspace_workflow.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileUploadResponse {
    private String message;
    private String workflowId;
}
