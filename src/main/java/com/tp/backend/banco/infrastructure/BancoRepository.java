package com.tp.backend.banco.infrastructure;

import com.tp.backend.banco.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Long> {
    boolean existsByCodigo(String codigo);
}