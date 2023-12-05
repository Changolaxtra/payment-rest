package com.bank.payments.api.repository;

public interface BankRepository<T, I> {
    boolean save(I key, T entity);
    T find(I key);
    void update(I key,T entity);
    boolean exists(I key);
}
