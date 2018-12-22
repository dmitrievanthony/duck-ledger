package com.dmitrievanthony.duck.ledger.core;

import com.dmitrievanthony.duck.ledger.core.exception.InsufficientFundsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests common for all implementations of {@link LedgerService}.
 */
public abstract class LedgerServiceTest {
    /** Ledger service for testing. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of ledger service test.
     *
     * @param ledgerService Ledger service for testing.
     */
    public LedgerServiceTest(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Test
    public void testClearAccount() {
        for (int expectedId = 1; expectedId < 10; expectedId++) {
            long id = ledgerService.createAccount();
            long balance = ledgerService.balance(id);

            assertEquals(expectedId, id);
            assertEquals(0, balance);
        }
    }

    @Test
    public void testWithdraw() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, 10);
        ledgerService.withdraw(id, 8);

        assertEquals(2, ledgerService.balance(id));
    }

    @Test(expected = InsufficientFundsException.class)
    public void testWithdrawInsufficientFunds() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, 10);
        ledgerService.withdraw(id, 15);
    }

    @Test
    public void testWithdrawInsufficientFundsPostCheck() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, 10);

        try {
            ledgerService.withdraw(id, 15);
        }
        catch (Exception e) { /* Ignore. */ }

        assertEquals(10, ledgerService.balance(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawAccountDoesNotExist() {
        ledgerService.withdraw(42, 0);
    }

    @Test
    public void testDeposit() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, 10);

        assertEquals(10, ledgerService.balance(id));
    }

    @Test(expected = ArithmeticException.class)
    public void testDepositLongOverflow() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, Long.MAX_VALUE);
        ledgerService.deposit(id, 1);
    }

    @Test
    public void testDepositLongOverflowPostCheck() {
        long id = ledgerService.createAccount();
        ledgerService.deposit(id, Long.MAX_VALUE);

        try {
            ledgerService.deposit(id, 1);
        }
        catch (Exception e) { /* Ignore. */ }

        assertEquals(Long.MAX_VALUE, ledgerService.balance(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositAccountDoesNotExist() {
        ledgerService.deposit(42, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBalanceAccountDoesNotExist() {
        ledgerService.balance(42);
    }

    @Test
    public void testTransfer() {
        long fromId = ledgerService.createAccount();
        long toId = ledgerService.createAccount();

        ledgerService.deposit(fromId, 10);

        ledgerService.transfer(fromId, toId, 8);

        assertEquals(2, ledgerService.balance(fromId));
        assertEquals(8, ledgerService.balance(toId));
    }

    @Test(expected = InsufficientFundsException.class)
    public void testTransferInsufficientFunds() {
        long fromId = ledgerService.createAccount();
        long toId = ledgerService.createAccount();

        ledgerService.deposit(fromId, 10);

        ledgerService.transfer(fromId, toId, 15);
    }

    @Test
    public void testTransferInsufficientFundsPostCheck() {
        long fromId = ledgerService.createAccount();
        long toId = ledgerService.createAccount();

        ledgerService.deposit(fromId, 10);

        try {
            ledgerService.transfer(fromId, toId, 15);
        }
        catch (Exception e) { /* Ignore. */ }

        assertEquals(10, ledgerService.balance(fromId));
    }

    @Test(expected = ArithmeticException.class)
    public void testTransferLongOverflow() {
        long fromId = ledgerService.createAccount();
        long toId = ledgerService.createAccount();

        ledgerService.deposit(fromId, 1);
        ledgerService.deposit(toId, Long.MAX_VALUE);

        ledgerService.transfer(fromId, toId, 1);
    }

    @Test
    public void testTransferLongOverflowPostCheck() {
        long fromId = ledgerService.createAccount();
        long toId = ledgerService.createAccount();

        ledgerService.deposit(fromId, 1);
        ledgerService.deposit(toId, Long.MAX_VALUE);

        try {
            ledgerService.transfer(fromId, toId, 1);
        }
        catch (Exception e) { /* Ignore. */ }

        assertEquals(1, ledgerService.balance(fromId));
        assertEquals(Long.MAX_VALUE, ledgerService.balance(toId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFromAccountDoesNotExist() {
        long id = ledgerService.createAccount();
        ledgerService.transfer(42, id, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferToAccountDoesNotExist() {
        long id = ledgerService.createAccount();
        ledgerService.transfer(id, 42, 0);
    }
}
