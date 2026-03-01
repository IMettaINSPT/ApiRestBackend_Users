package com.tp.backend.common.application;

public interface IUpdateUseCase<R,C> {
    R actualizar(Long id, C request);
}