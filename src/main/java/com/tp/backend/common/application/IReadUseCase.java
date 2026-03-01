package com.tp.backend.common.application;

import java.util.List;

public interface IReadUseCase<R> {

    List<R> listar();

    R obtener(Long id);
}