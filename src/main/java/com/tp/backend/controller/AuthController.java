package com.tp.backend.controller;

import com.tp.backend.dto.*;
import com.tp.backend.dto.login.LoginRequest;
import com.tp.backend.dto.login.LoginResponse;
import com.tp.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }

    @GetMapping("/me")
    public MeResponse me(org.springframework.security.core.Authentication auth) {

        // username viene del subject (sub)
        String username = auth.getName();

        // rol viene del claim convertido a authority
        String rol = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        return new MeResponse(username, rol);
    }

}
