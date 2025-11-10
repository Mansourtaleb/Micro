package tn.esprit.workspace_workflow.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service" , url = "${application.config.users-url}")
public interface UserClient {

    @GetMapping("/workspace/{workspace-id}")
    List<User> fundAllUsersByWorkspace(@PathVariable("workspace-id") String workspaceId);

}