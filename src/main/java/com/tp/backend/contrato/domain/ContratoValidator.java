package com.tp.backend.contrato.domain;


public interface ContratoValidator<T> {
    void validar(T request);
}