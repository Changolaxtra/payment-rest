package com.bank.payments.api.controller;

import com.bank.payments.api.dto.CreateCardRequest;
import com.bank.payments.api.dto.CreateCardResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateCardResponse create(@RequestBody final CreateCardRequest request) {
        // TODO Add implementation to store new credit card.
        return CreateCardResponse.builder().number(request.getNumber()).successful(true).build();
    }
}
