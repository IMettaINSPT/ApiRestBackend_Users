package com.tp.backend.config;

import com.tp.backend.model.Role;
import com.tp.backend.model.Usuario;
import com.tp.backend.repository.RoleRepository;
import com.tp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username:admin}")
    private String adminUsername;

    @Value("${app.bootstrap.admin.password:admin1234}")
    private String adminPassword;

    public DataSeeder(RoleRepository roleRepo,
                      UsuarioRepository usuarioRepo,
                      PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // 1) Roles base
        Role adminRole = roleRepo.findByNombre("ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ADMIN")));

        roleRepo.findByNombre("USER")
                .orElseGet(() -> roleRepo.save(new Role("USER")));

        // 2) Admin por defecto (solo si no existe)
        if (!usuarioRepo.existsByUsername(adminUsername)) {
            Usuario admin = new Usuario(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    adminRole
            );
            usuarioRepo.save(admin);
            System.out.println("[DataSeeder] Admin creado: " + adminUsername);
        } else {
            System.out.println("[DataSeeder] Admin ya existe: " + adminUsername);
        }
    }
}
