package com.tp.backend.repository;

import com.tp.backend.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BancoRepository extends JpaRepository<Banco, Long> {
    Optional<Banco> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}
