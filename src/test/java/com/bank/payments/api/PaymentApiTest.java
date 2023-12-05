package com.bank.payments.api;

import com.bank.payments.api.dto.CardPaymentRequest;
import com.bank.payments.api.dto.CardPaymentResponse;
import com.bank.payments.api.dto.CreateCardRequest;
import com.bank.payments.api.dto.CreateCardResponse;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentApiTest extends JsonApiTest {

    private static final String CREATE_CARD_ENDPOINT = "/card";
    private static final String CARD_NUMBER = "1111222233334444";
    private static final int CVV = 123;
    private static final BigDecimal BALANCE = new BigDecimal(1000);
    private static final String PROCESS_PAYMENT_ENDPOINT = "/card/payment/process";
    private static final int WRONG_CVV = 456;

    //@Test
    public void createCreditCardAndMakePayment() throws Exception {
        // Create Credit Card, should return Successful=true
        final CreateCardResponse createCardResponse1 = createCard(CARD_NUMBER, CVV, BALANCE);
        assertTrue(createCardResponse1.isSuccessful());

        // Create the same credit card, should return Successful=false
        final CreateCardResponse createCardResponse2 = createCard(CARD_NUMBER, CVV, BALANCE);
        assertFalse(createCardResponse2.isSuccessful());

        // Process payment with wrong cvv, should return Successful=false
        final CardPaymentResponse paymentResponse1 = makePayment(CREATE_CARD_ENDPOINT, WRONG_CVV, new BigDecimal(100));
        assertFalse(paymentResponse1.isSuccessful());

        // Process payment with correct cvv and enough balance, should return Successful=true
        final CardPaymentResponse paymentResponse2 = makePayment(CREATE_CARD_ENDPOINT, CVV, new BigDecimal(700));
        assertTrue(paymentResponse2.isSuccessful());
        assertEquals(new BigDecimal(300), paymentResponse2.getBalance());

        // Process payment with correct cvv and not enough balance, should return Successful=false
        final CardPaymentResponse paymentResponse3 = makePayment(CREATE_CARD_ENDPOINT, CVV, new BigDecimal(500));
        assertFalse(paymentResponse2.isSuccessful());
    }

    private CreateCardResponse createCard(final String cardNumber,
                                          final Integer cvv,
                                          final BigDecimal balance) throws Exception {

        final CreateCardRequest createCardRequest = new CreateCardRequest(cardNumber, cvv, balance);
        final String jsonRequest = super.mapToJson(createCardRequest);
        return makeApiCall(jsonRequest, CREATE_CARD_ENDPOINT, CreateCardResponse.class);
    }

    private CardPaymentResponse makePayment(final String cardNumber,
                                            final Integer cvv,
                                            final BigDecimal amount) throws Exception {

        final CardPaymentRequest cardPaymentRequest = new CardPaymentRequest(cardNumber, cvv, amount);
        final String jsonRequest = super.mapToJson(cardPaymentRequest);
        return makeApiCall(jsonRequest, PROCESS_PAYMENT_ENDPOINT, CardPaymentResponse.class);
    }

}
