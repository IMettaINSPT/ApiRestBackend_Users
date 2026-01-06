package com.tp.backend.config;

import com.tp.backend.model.UsuarioAdmin;
import com.tp.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username:admin}")
    private String adminUsername;

    @Value("${app.bootstrap.admin.password:admin1234}")
    private String adminPassword;

    public DataSeeder(UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Crear usuario ADMIN por defecto si no existe
        if (!usuarioRepository.existsByUsername(adminUsername)) {

            UsuarioAdmin admin = new UsuarioAdmin();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);

            usuarioRepository.save(admin);

            System.out.println("[DataSeeder] Usuario ADMIN creado: " + adminUsername);
        } else {
            System.out.println("[DataSeeder] Usuario ADMIN ya existe: " + adminUsername);
        }
    }
}
