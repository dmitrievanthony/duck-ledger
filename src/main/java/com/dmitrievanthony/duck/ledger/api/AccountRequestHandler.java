package com.dmitrievanthony.duck.ledger.api;

import com.dmitrievanthony.duck.ledger.core.LedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Account request handler.
 */
public class AccountRequestHandler implements Route {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(AccountRequestHandler.class);

    /** Ledger service. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of account request handler.
     *
     * @param ledgerService Ledger service.
     */
    public AccountRequestHandler(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    /** {@inheritDoc} */
    @Override public Object handle(Request request, Response response) throws Exception {
        long id = ledgerService.createAccount();

        log.info("Account successfully created [id=" + id + "]");

        response.status(201);
        return id;
    }
}
