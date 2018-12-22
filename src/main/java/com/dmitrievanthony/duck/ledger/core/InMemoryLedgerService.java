package com.dmitrievanthony.duck.ledger.core;

import com.dmitrievanthony.duck.ledger.core.exception.InsufficientFundsException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of {@link LedgerService} that uses in-memory data structures to maintain account balances.
 */
public class InMemoryLedgerService implements LedgerService {
    /** Account balances. */
    private final ConcurrentMap<Long, Long> balances = new ConcurrentHashMap<>();

    /** Locks that protect access to account balances. */
    private final ConcurrentMap<Long, Lock> locks = new ConcurrentHashMap<>();

    /** Sequence of account identifiers. */
    private final AtomicLong idSequence = new AtomicLong(1);

    /** {@inheritDoc} */
    @Override public long createAccount() {
        long id = idSequence.getAndIncrement();
        Long prev = balances.putIfAbsent(id, 0L);

        if (prev != null)
            throw new IllegalStateException("Account already exists [id=" + id + "]");

        return id;
    }

    /** {@inheritDoc} */
    @Override public void transfer(long fromId, long toId, long amount) {
        checkAccountExists(fromId);
        checkAccountExists(toId);

        Lock fromLock = locks.computeIfAbsent(fromId, k -> new ReentrantLock());
        Lock toLock = locks.computeIfAbsent(toId, k -> new ReentrantLock());

        // Here we always need to lock account in the same order to avoid deadlocks.
        if (fromId > toId) {
            fromLock.lock();
            toLock.lock();
        }
        else {
            toLock.lock();
            fromLock.lock();
        }

        long fromBalance = balances.get(fromId);
        long toBalance = balances.get(toId);

        try {
            withdraw(fromId, amount);
            deposit(toId, amount);
        }
        catch (Exception e) {
            // In case of any exception balances should be recovered.
            balances.put(fromId, fromBalance);
            balances.put(toId, toBalance);

            throw e;
        }
        finally {
            toLock.unlock();
            fromLock.unlock();
        }
    }

    /** {@inheritDoc} */
    @Override public void withdraw(long id, long amount) {
        checkAccountExists(id);

        balances.compute(id, (accId, prevAmount) -> {
            long newAmount = Math.subtractExact(prevAmount, amount);
            if (newAmount < 0)
                throw new InsufficientFundsException();

            return newAmount;
        });
    }

    /** {@inheritDoc} */
    @Override public void deposit(long id, long amount) {
        checkAccountExists(id);

        balances.compute(id, (accId, prevAmount) -> Math.addExact(prevAmount, amount));
    }

    /** {@inheritDoc} */
    @Override public long balance(long id) {
        checkAccountExists(id);

        return balances.get(id);
    }

    /**
     * Checks that account with the specified identifier exists. Throws {@link IllegalArgumentException} if account does
     * not exist.
     *
     * @param id Account identifier.
     */
    private void checkAccountExists(long id) {
        if (!balances.containsKey(id))
            throw new IllegalArgumentException("Account not found [id=" + id + "]");
    }
}
