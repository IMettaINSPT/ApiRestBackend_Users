package com.tp.backend.juez.domain;

public interface JuezValidator<T> {
    void validar(T request);
}
