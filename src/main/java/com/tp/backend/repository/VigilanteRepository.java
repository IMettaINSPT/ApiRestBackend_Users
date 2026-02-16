package com.tp.backend.repository;

import com.tp.backend.model.Vigilante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional; // Agregado para soportar findByCodigo

public interface VigilanteRepository extends JpaRepository<Vigilante, Long> {
    boolean existsByCodigo(String codigo);

    // --- AGREGADO PARA EL SERVICE ---
    Optional<Vigilante> findByCodigo(String codigo);
    // --------------------------------

    // Vigilantes que NO están asociados a ningún UsuarioVigilante
    //pasarlo a una funcion luego, no deberia haber qrys hardoceadas
    @Query("""
           select v
           from Vigilante v
           where not exists (
               select 1
               from UsuarioVigilante uv
               where uv.perfil = v
           )
           """)
    List<Vigilante> findDisponibles();
}