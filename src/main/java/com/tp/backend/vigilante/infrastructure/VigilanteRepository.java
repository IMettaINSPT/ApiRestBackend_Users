package com.tp.backend.vigilante.infrastructure;

import com.tp.backend.vigilante.domain.Vigilante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VigilanteRepository extends JpaRepository<Vigilante, Long> {
    boolean existsByCodigo(String codigo);
    Optional<Vigilante> findByCodigo(String codigo);

    @Query("""
           select v from Vigilante v
           where not exists (
               select 1 from UsuarioVigilante uv where uv.perfil = v
           )
           """)
    List<Vigilante> findDisponibles();
}