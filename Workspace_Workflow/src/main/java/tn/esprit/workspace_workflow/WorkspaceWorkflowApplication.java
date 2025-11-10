package tn.esprit.workspace_workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WorkspaceWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run ( WorkspaceWorkflowApplication.class, args );
    }

}
