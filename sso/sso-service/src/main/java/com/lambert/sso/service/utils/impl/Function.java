package com.lambert.sso.service.utils.impl;

public interface Function<E,T> {

    public T callback(E e);
}
