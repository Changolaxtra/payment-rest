package com.bank.payments.api.thirdparty.repository;

import com.bank.payments.api.thirdparty.model.CreditCard;
import com.bank.payments.api.thirdparty.exception.BankRepositoryException;
import com.bank.payments.api.thirdparty.exception.ErrorMessage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class InMemoryCreditCardRepository implements CreditCardRepository {

    private Map<String, CreditCard> numberToCreditCard;

    @PostConstruct
    public void init() {
        numberToCreditCard = new HashMap<>();
    }

    @Override
    public CreditCard save(final CreditCard creditCard) throws BankRepositoryException {
        log.info("Saving {}", creditCard);
        validateCardForSave(creditCard);
        numberToCreditCard.put(creditCard.number(),
                new CreditCard(creditCard.number(),
                        creditCard.cvv(),
                        creditCard.balance()));
        return numberToCreditCard.get(creditCard.number());
    }

    @Override
    public CreditCard find(final String cardNumber) throws BankRepositoryException {
        log.info("Searching {}", cardNumber);
        validateExistence(cardNumber);
        return numberToCreditCard.get(cardNumber);
    }

    @Override
    public CreditCard update(final CreditCard updatedCard) throws BankRepositoryException {
        log.info("Updating {}", updatedCard);
        validateCardForUpdate(updatedCard);
        numberToCreditCard.put(updatedCard.number(),
                new CreditCard(updatedCard.number(),
                        updatedCard.cvv(),
                        updatedCard.balance()));

        return numberToCreditCard.get(updatedCard.number());
    }

    @Override
    public void remove(String cardNumber) throws BankRepositoryException {
        log.info("Removing Card: {}", cardNumber);
        validateExistence(cardNumber);
        numberToCreditCard.remove(cardNumber);
    }

    @Override
    public boolean exists(final String cardNumber) throws BankRepositoryException {
        log.info("Exists? {}", cardNumber);
        if (StringUtils.isEmpty(cardNumber)) {
            throw new BankRepositoryException(ErrorMessage.INVALID_CARD_NUMBER);
        }
        return numberToCreditCard.containsKey(cardNumber);
    }

    private void validateCardForSave(final CreditCard creditCard) throws BankRepositoryException {
        validateNullCard(creditCard);
        validateAvailability(creditCard);
    }

    private void validateAvailability(CreditCard creditCard) throws BankRepositoryException {
        if (exists(creditCard.number())) {
            throw new BankRepositoryException(ErrorMessage.CARD_ALREADY_EXISTS);
        }
    }

    private void validateCardForUpdate(final CreditCard updatedCard) throws BankRepositoryException {
        validateNullCard(updatedCard);
        validateExistence(updatedCard.number());
    }

    private void validateNullCard(CreditCard creditCard) throws BankRepositoryException {
        if (Objects.isNull(creditCard)) {
            throw new BankRepositoryException(ErrorMessage.CARD_IS_NULL);
        }
    }

    private void validateExistence(String cardNumber) throws BankRepositoryException {
        if (isNotPresent(cardNumber)) {
            throw new BankRepositoryException(ErrorMessage.CARD_DOES_NOT_EXISTS);
        }
    }

    private boolean isNotPresent(final String cardNumber) throws BankRepositoryException {
        return !exists(cardNumber);
    }
}
