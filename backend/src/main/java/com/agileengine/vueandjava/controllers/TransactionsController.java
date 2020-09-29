package com.agileengine.vueandjava.controllers;

import com.agileengine.vueandjava.models.Transaction;
import com.agileengine.vueandjava.models.TransactionBody;
import com.agileengine.vueandjava.servises.TransactionService;
import com.agileengine.vueandjava.servises.TransactionValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/api/account")
public class TransactionsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public BigDecimal getCurrentAccountBalance() {
        return transactionService.getBalance();
    }

    @GetMapping("/transactions")
    public Collection<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @PostMapping("/transactions")
    public Transaction postTransaction(@RequestBody final TransactionBody body) {
        try {
            return transactionService.createTransaction(body);
        } catch (TransactionValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create transaction due to validation error", e);
        }
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable("id") final String id) {
        final Transaction result = transactionService.getTransactionById(id);
        if (result == null) {
            logger.info("Transaction with id={} is not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found by given id");
        }
        return result;
    }


}
