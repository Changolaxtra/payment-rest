package com.bank.payments.api.repository;

import com.bank.payments.api.model.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.Map;

// TODO Add implementation of the methods to store, read and update the credit card and balance.
@Repository
public class CreditCardRepository implements BankRepository<String, CreditCard> {

    private Map<String, CreditCard> numberToCreditCard;

    @Override
    public boolean save(CreditCard key, String entity) {
        return false;
    }

    @Override
    public String find(CreditCard key) {
        return null;
    }

    @Override
    public void update(CreditCard key, String entity) {

    }

    @Override
    public boolean exists(CreditCard key) {
        return false;
    }
}
