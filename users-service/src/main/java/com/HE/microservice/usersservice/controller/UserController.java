package com.HE.microservice.usersservice.controller;


// package com.example.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/public/info")
    public ResponseEntity<String> publicInfo() {
        return ResponseEntity.ok("Public access - no authentication required!");
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> userInfo() {
        return ResponseEntity.ok("User access granted!");
    }

    @GetMapping("/admin/info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminInfo() {
        return ResponseEntity.ok("Admin access granted!");
    }
}
