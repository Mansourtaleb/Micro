package com.HE.microservice.usersservice.controller;


// package com.example.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(
            @org.springframework.security.core.annotation.AuthenticationPrincipal
            org.springframework.security.oauth2.jwt.Jwt jwt,
            org.springframework.security.core.Authentication authentication
    ) {
        var roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())         // ROLE_*
                .filter(s -> s.startsWith("ROLE_"))
                .map(s -> s.substring("ROLE_".length()))
                .filter(r -> r.equals("USER") || r.equals("ADMIN"))
                .toList();

        Map<String, Object> body = Map.of(
                "sub", jwt.getSubject(),
                "username", jwt.getClaimAsString("preferred_username"),
                "name", jwt.getClaimAsString("name"),
                "email", jwt.getClaimAsString("email"),
                "roles", roles,
                "aud", jwt.getAudience(),
                "issuer", jwt.getIssuer().toString(),
                "expiresAt", String.valueOf(jwt.getExpiresAt())
        );
        return ResponseEntity.ok(body);
    }


}
