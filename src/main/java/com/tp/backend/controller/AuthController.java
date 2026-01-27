package com.tp.backend.controller;

import com.tp.backend.dto.*;
import com.tp.backend.dto.login.LoginRequest;
import com.tp.backend.dto.login.LoginResponse;
import com.tp.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        log.info("Post /api/auth/login userName{}",req.username);
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

        var jwt = (org.springframework.security.oauth2.jwt.Jwt) auth.getPrincipal();
        Long rolId = jwt.getClaim("roleId");
        return new MeResponse(username, rol, rolId);    }

}
