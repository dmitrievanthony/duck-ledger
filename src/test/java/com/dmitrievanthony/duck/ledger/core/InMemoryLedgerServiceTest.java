package com.dmitrievanthony.duck.ledger.core;

/**
 * Tests for {@link InMemoryLedgerService}.
 */
public class InMemoryLedgerServiceTest extends LedgerServiceTest {
    /** Constructs a new instance of in-memory ledger service test. */
    public InMemoryLedgerServiceTest() {
        super(new InMemoryLedgerService());
    }
}
