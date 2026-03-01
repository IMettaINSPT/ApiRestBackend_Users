package com.tp.backend.sucursal.domain;

public interface SucursalValidator<T> {
    void validar(T request);
}