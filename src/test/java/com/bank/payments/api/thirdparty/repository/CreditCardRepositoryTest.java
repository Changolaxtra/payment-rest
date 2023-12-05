package com.bank.payments.api.thirdparty.repository;

import com.bank.payments.api.model.CreditCard;
import com.bank.payments.api.thirdparty.exception.BankRepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardRepositoryTest {

    private static final String CARD_NUMBER = "1111222233334444";
    private static final String CARD_NUMBER2 = "1111222233335555";

    private CreditCardRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CreditCardRepository();
        repository.init();
        repository.save(CARD_NUMBER, new CreditCard(CARD_NUMBER, 123, new BigDecimal(500)));
    }

    @Test
    public void givenExitingCardNumberAndSavingItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.save(CARD_NUMBER, new CreditCard(CARD_NUMBER, 123, new BigDecimal(500)));
        });

        assertNotNull(exception);
        assertEquals("Card already exists", exception.getMessage());
    }

    @Test
    public void givenMismatchCardNumberAndSavingItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.save(CARD_NUMBER2, new CreditCard(CARD_NUMBER, 123, new BigDecimal(500)));
        });

        assertNotNull(exception);
        assertEquals("Card Numbers mismatch", exception.getMessage());
    }

    @Test
    public void givenNonexistentCardNumberToFindItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.find(CARD_NUMBER2);
        });

        assertNotNull(exception);
        assertEquals("Card does not exist", exception.getMessage());
    }

    @Test
    public void givenExistentCardNumberToFindItShouldReturnTheCorrectOne() {
        final CreditCard creditCard = repository.find(CARD_NUMBER);

        assertNotNull(creditCard);
        assertEquals(CARD_NUMBER, creditCard.number());
        assertEquals(123, creditCard.cvv());
        assertEquals(new BigDecimal(500), creditCard.balance());
    }

    @Test
    public void givenNonexistentCardNumberAndUpdatingItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.update(CARD_NUMBER2, new CreditCard(CARD_NUMBER2, 123, new BigDecimal(600)));
        });

        assertNotNull(exception);
        assertEquals("Card does not exist", exception.getMessage());
    }

    @Test
    public void givenNullCardAndUpdatingItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.update(CARD_NUMBER, null);
        });

        assertNotNull(exception);
        assertEquals("Updated card is null", exception.getMessage());
    }

    @Test
    public void givenMismatchCardNumberAndUpdatingItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.update(CARD_NUMBER, new CreditCard(CARD_NUMBER2, 123, new BigDecimal(600)));
        });

        assertNotNull(exception);
        assertEquals("Card Numbers mismatch", exception.getMessage());
    }

    @Test
    public void givenCorrectCardNumberAndUpdatingItShouldThrowException() {
        final CreditCard creditCard =
                repository.update(CARD_NUMBER, new CreditCard(CARD_NUMBER, 123, new BigDecimal(700)));

        assertNotNull(creditCard);
        assertEquals(CARD_NUMBER, creditCard.number());
        assertEquals(123, creditCard.cvv());
        assertEquals(new BigDecimal(700), creditCard.balance());
    }

    @Test
    public void givenInvalidCardNumberAndLookingForItShouldThrowException() {
        Exception exception = assertThrows(BankRepositoryException.class, () -> {
            repository.exists(null);
        });

        assertNotNull(exception);
        assertEquals("Invalid Card number : null or blank", exception.getMessage());
    }
}