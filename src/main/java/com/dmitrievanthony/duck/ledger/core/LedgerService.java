package com.dmitrievanthony.duck.ledger.core;

/**
 * Ledger service that maintains account balances and allows to make transactions, withdraws and deposits. This service
 * is thread-safe.
 */
public interface LedgerService {
    /**
     * Creates a new account.
     *
     * @return Account identifier.
     */
    public long createAccount();

    /**
     * Transfers funds between specified accounts.
     *
     * @param fromId Identifier of the account for withdraw.
     * @param toId Identifier of the account for deposit.
     * @param amount Funds amount.
     */
    public void transfer(long fromId, long toId, long amount);

    /**
     * Withdraws funds from the specified account.
     *
     * @param id Account identifier.
     * @param amount Funds amount.
     */
    public void withdraw(long id, long amount);

    /**
     * Deposits funds on the specified account.
     *
     * @param id Account identifier.
     * @param amount Funds amount.
     */
    public void deposit(long id, long amount);

    /**
     * Returns funds amount for the specified account.
     *
     * @param id Account identifier.
     * @return Funds amount.
     */
    public long balance(long id);
}
