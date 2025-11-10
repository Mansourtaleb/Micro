package tn.esprit.workspace_workflow.client;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    private String name;
    private String email;
    private Role role;

    public boolean isManager() {
        return this.role == Role.MANAGER;
    }
    public boolean isEmployee() {
        return this.role == Role.EMPLOYEE;
    }
}