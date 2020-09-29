package com.agileengine.vueandjava.servises;

import com.agileengine.vueandjava.constants.TransactionType;
import com.agileengine.vueandjava.models.Transaction;
import com.agileengine.vueandjava.models.TransactionBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

@Component
public class TransactionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StorageService storageService;

    public BigDecimal getBalance() {
        logger.info("Calculating current balance");
        final BigDecimal balance = this.storageService.getAllTransactions().stream().map(entry -> {
            if (TransactionType.CREDIT.equals(entry.getType())) {
                return entry.getAmount();
            } else {
                return entry.getAmount().negate();
            }
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        logger.info("Current balance={}", balance);
        return balance;
    }

    public Collection<Transaction> getTransactions() {
        return this.storageService.getAllTransactions();
    }

    public Transaction createTransaction(final TransactionBody body) throws TransactionValidationException {
        validateTransactionBody(body);
        final String uniqueID = UUID.randomUUID().toString();
        final Transaction transaction = new Transaction()
                .setId(uniqueID)
                .setType(body.getType())
                .setAmount(body.getAmount())
                .setEffectiveDate(Calendar.getInstance().getTime());
        return this.storageService.putTransaction(uniqueID, transaction);
    }

    private void validateTransactionBody(final TransactionBody body) throws TransactionValidationException {
        if (TransactionType.CREDIT.equals(body.getType())) {
            return;
        }
        logger.info("Validate current balance before adding debit transaction with amount={}", body.getAmount());
        final BigDecimal currentBalance = this.getBalance();
        if (currentBalance.compareTo(body.getAmount()) < 0) {
            logger.error("Debit transaction cannot be created as amount exceeds current balance");
            throw new TransactionValidationException("Debit transaction cannot be created as amount exceeds current balance");
        }
    }

    public Transaction getTransactionById(final String id) {
        return this.storageService.getTransactionById(id);
    }
}
