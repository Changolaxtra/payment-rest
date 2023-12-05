package com.bank.payments.api.thirdparty.repository;

public interface BankRepository<T, I> {
    T save(I key, T entity);
    T find(I key);
    T update(I key,T entity);
    boolean exists(I key);
}
