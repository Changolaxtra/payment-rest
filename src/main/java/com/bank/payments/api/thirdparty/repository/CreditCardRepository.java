package com.bank.payments.api.thirdparty.repository;

import com.bank.payments.api.model.CreditCard;
import com.bank.payments.api.thirdparty.exception.BankRepositoryException;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class CreditCardRepository implements BankRepository<CreditCard, String> {

    private Map<String, CreditCard> numberToCreditCard;

    @PostConstruct
    public void init() {
        numberToCreditCard = new HashMap<>();
    }

    @Override
    public CreditCard save(final String cardNumber, final CreditCard creditCard) {
        validateCardForSave(cardNumber, creditCard);
        return numberToCreditCard.put(cardNumber, creditCard);
    }

    @Override
    public CreditCard find(final String cardNumber) {
        if (isNotPresent(cardNumber)) {
            throw new BankRepositoryException("Card does not exist");
        }
        return numberToCreditCard.get(cardNumber);
    }

    @Override
    public CreditCard update(final String cardNumber, final CreditCard updatedCard) {
        validateCardForUpdate(cardNumber, updatedCard);
        numberToCreditCard.put(cardNumber,
                new CreditCard(cardNumber,
                        updatedCard.cvv(),
                        updatedCard.balance()));

        return numberToCreditCard.get(cardNumber);
    }

    @Override
    public boolean exists(final String cardNumber) {
        if (StringUtils.isEmpty(cardNumber)) {
            throw new BankRepositoryException("Invalid Card number : null or blank");
        }
        return numberToCreditCard.containsKey(cardNumber);
    }

    private void validateCardForSave(final String cardNumber, final CreditCard creditCard) {
        if (exists(cardNumber)) {
            throw new BankRepositoryException("Card already exists");
        }

        if (!StringUtils.equalsIgnoreCase(cardNumber, creditCard.number())) {
            throw new BankRepositoryException("Card Numbers mismatch");
        }
    }

    private void validateCardForUpdate(final String cardNumber, final CreditCard updatedCard) {
        if (Objects.isNull(updatedCard)) {
            throw new BankRepositoryException("Updated card is null");
        }

        if (isNotPresent(cardNumber)) {
            throw new BankRepositoryException("Card does not exist");
        }

        if (!StringUtils.equalsIgnoreCase(cardNumber, updatedCard.number())) {
            throw new BankRepositoryException("Card Numbers mismatch");
        }
    }

    private boolean isNotPresent(final String cardNumber) {
        return !exists(cardNumber);
    }
}
