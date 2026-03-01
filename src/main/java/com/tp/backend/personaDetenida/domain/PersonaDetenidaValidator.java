package com.tp.backend.personaDetenida.domain;

public interface PersonaDetenidaValidator<T> {
    void validar(T request);
}
