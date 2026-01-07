package com.tp.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ping").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        // ejemplo de autorización por rol:
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
// banco
                                .requestMatchers(HttpMethod.POST, "/api/bancos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,  "/api/bancos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/bancos/**").hasRole("ADMIN")
                                //sucursal
                                .requestMatchers(HttpMethod.POST, "/api/sucursales/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,  "/api/sucursales/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/sucursales/**").hasRole("ADMIN")
                        //vigilantes
                                .requestMatchers(HttpMethod.POST, "/api/vigilantes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,  "/api/vigilantes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/vigilantes/**").hasRole("ADMIN")
                        //contratos
                                .requestMatchers(HttpMethod.POST, "/api/contratos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,  "/api/contratos/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/contratos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                );

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter gac = new JwtGrantedAuthoritiesConverter();
        // Vamos a usar claim "role": "ADMIN" -> ROLE_ADMIN
        gac.setAuthoritiesClaimName("role");
        gac.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(gac);
        return conv;
    }
}
