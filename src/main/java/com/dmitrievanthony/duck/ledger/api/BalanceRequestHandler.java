package com.dmitrievanthony.duck.ledger.api;

import com.dmitrievanthony.duck.ledger.core.LedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.dmitrievanthony.duck.ledger.api.util.ParamsParser.parseRequiredLongParam;

/**
 * Balance request handler.
 */
public class BalanceRequestHandler implements Route {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(BalanceRequestHandler.class);

    /** Ledger service. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of balance request handler.
     *
     * @param ledgerService Ledger service.
     */
    public BalanceRequestHandler(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    /** {@inheritDoc} */
    @Override public Object handle(Request request, Response response) throws Exception {
        long id = parseRequiredLongParam(request, ":id");

        long amount = ledgerService.balance(id);

        log.info("Balance successfully provided [id=" + id + "]");

        response.status(200);
        return amount;
    }
}
