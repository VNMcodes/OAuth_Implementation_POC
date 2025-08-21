package com.example.resourceserver.web;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/public")
    public Map<String, Object> publicEndpoint() {
        return Map.of("message", "This is a public endpoint");
    }

    @GetMapping("/api/hello")
    public Map<String, Object> hello(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
            "message", "Hello from the resource server!",
            "subject", jwt.getSubject(),
            "scopes", jwt.getClaimAsStringList("scope")
        );
    }

    @GetMapping("/api/me")
    public ResponseEntity<Map<String, Object>> me(Principal principal) {
        return ResponseEntity.ok(Map.of("name", principal.getName()));
    }
}

