package com.dmitrievanthony.duck.ledger.api;

import com.dmitrievanthony.duck.ledger.core.LedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.dmitrievanthony.duck.ledger.api.util.ParamsParser.parseRequiredLongParam;
import static com.dmitrievanthony.duck.ledger.api.util.ParamsParser.parseRequiredLongQueryParam;

/**
 * Deposit request handler.
 */
public class DepositRequestHandler implements Route {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);

    /** Ledger service. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of deposit request handler.
     *
     * @param ledgerService Ledger service.
     */
    public DepositRequestHandler(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    /** {@inheritDoc} */
    @Override public Object handle(Request request, Response response) throws Exception {
        long id = parseRequiredLongParam(request, ":id");
        long amount = parseRequiredLongQueryParam(request, "amount");

        ledgerService.deposit(id, amount);

        log.info("Deposit successfully processed [id=" + id + "]");

        response.status(202);
        return "";
    }
}
