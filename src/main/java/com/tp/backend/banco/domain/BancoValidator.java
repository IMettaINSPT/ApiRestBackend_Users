package com.tp.backend.banco.domain;

public interface BancoValidator<T> {
    void validar(T request);
}