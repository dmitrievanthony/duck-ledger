package com.dmitrievanthony.duck.ledger.core.exception;

/**
 * Insufficient funds exception.
 */
public class InsufficientFundsException extends RuntimeException {
    /**
     * Constructs a new instance of insufficient funds exception.
     */
    public InsufficientFundsException() {
        super("Insufficient Funds");
    }
}
