package com.tp.backend.repository;

import com.tp.backend.model.UsuarioVigilante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioVigilanteRepository extends JpaRepository<UsuarioVigilante, Long> {
    boolean existsByPerfil_Id(Long vigilanteId);
}
