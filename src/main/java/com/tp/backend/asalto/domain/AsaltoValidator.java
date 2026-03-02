package com.tp.backend.asalto.domain;

public interface AsaltoValidator<T> {
    void validar(T request);
}