package com.bank.payments.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class CardPaymentResponse {
    private boolean successful;
    private BigDecimal balance;
}
