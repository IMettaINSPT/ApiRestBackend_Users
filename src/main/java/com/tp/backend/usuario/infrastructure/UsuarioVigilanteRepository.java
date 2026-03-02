package com.tp.backend.usuario.infrastructure;

import com.tp.backend.usuario.domain.UsuarioVigilante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioVigilanteRepository extends JpaRepository<UsuarioVigilante, Long> {
    boolean existsByPerfil_Id(Long vigilanteId);
}
