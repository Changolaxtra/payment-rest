package com.bank.payments.api.repository;

import com.bank.payments.api.model.CreditCard;

import java.util.Map;

// TODO Add implementation of the methods to store, read and update the credit card and balance.
public class CreditCardRepository implements BankRepository<CreditCard,String> {

    private Map<String, CreditCard> numberToCreditCard;

    @Override
    public boolean save(String key, CreditCard entity) {
        return false;
    }

    @Override
    public CreditCard find(String key) {
        return null;
    }

    @Override
    public void update(String key, CreditCard entity) {

    }

    @Override
    public boolean exists(String key) {
        return false;
    }
}
