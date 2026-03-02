package com.tp.backend.usuario.controller.login;

import com.tp.backend.usuario.application.login.AuthUseCase;
import com.tp.backend.usuario.dto.login.LoginRequest;
import com.tp.backend.usuario.dto.login.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return authUseCase.login(req);
    }
}
