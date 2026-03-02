package com.tp.backend.banda.domain;

public interface BandaValidator<T> {
    void validar(T request);
}
