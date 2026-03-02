package com.tp.backend.juicio.domain;

public interface JuicioValidator<T> {
    void validar(T request);
}