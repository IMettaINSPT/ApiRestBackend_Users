package com.tp.backend.vigilante.domain;

public interface VigilanteValidator<T> {
    void validar(T request);
}