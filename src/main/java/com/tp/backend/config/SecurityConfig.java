package com.tp.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    // Se lee desde application.properties
    @Value("${app.cors.allowed-origins}")
    private String allowedOriginPatterns;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Habilitar CORS (usa el bean de abajo)
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ✅ Preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers("/ping").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()

                        // PERFILES PROPIOS (Acceso compartido)
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").hasAnyRole("ADMIN", "INVESTIGADOR", "VIGILANTE")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/me").hasAnyRole("ADMIN", "INVESTIGADOR", "VIGILANTE")
                        .requestMatchers(HttpMethod.GET, "/api/vigilantes/me").hasRole("VIGILANTE")

                        // --- REGLA CLAVE: INVESTIGADOR Y ADMIN PUEDEN CONSULTAR TODO ---
                        // Esto incluye /api/usuarios, /api/contratos, /api/vigilantes, etc.
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "INVESTIGADOR")

                        // --- ADMIN: SOLO ADMIN PUEDE MODIFICAR (POST, PUT, DELETE) ---
                        .requestMatchers(HttpMethod.POST,   "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                );

        return http.build();
    }

    /**
     * CORS configuration usando allowedOriginPatterns
     * (permite IPs dinámicas de LAN sin recompilar)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // Parsear propiedad app.cors.allowed-origins
        List<String> patterns = Arrays.stream(allowedOriginPatterns.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        config.setAllowedOriginPatterns(patterns);

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));

        // JWT Bearer → no cookies
        config.setAllowCredentials(false);

        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter gac = new JwtGrantedAuthoritiesConverter();
        gac.setAuthoritiesClaimName("role");
        gac.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(gac);
        return conv;
    }
}