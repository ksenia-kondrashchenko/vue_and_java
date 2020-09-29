package com.agileengine.vueandjava.servises;

import com.agileengine.vueandjava.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class StorageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    private final Map<String, Transaction> storage = new HashMap<>();

    public Collection<Transaction> getAllTransactions() {
        readLock.lock();
        try {
            return this.storage.values();
        } finally {
            readLock.unlock();
        }
    }

    public Transaction getTransactionById(final String id) {
        readLock.lock();
        try {
            return this.storage.get(id);
        } finally {
            readLock.unlock();
        }
    }

    public Transaction putTransaction(final String id, final Transaction transaction) {
        writeLock.lock();
        try {
            this.storage.put(id, transaction);
            logger.info("Transaction created with id={}, type={}, amount={}", id, transaction.getType(), transaction.getAmount());
            return transaction;
        } finally {
            writeLock.unlock();
        }
    }
}
