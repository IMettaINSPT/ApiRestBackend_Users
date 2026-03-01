package com.tp.backend.common.application;

public interface ICreateUseCase<R, C> {
    R crear(C request);
}