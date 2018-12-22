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
 * Withdraw request handler.
 */
public class WithdrawRequestHandler implements Route {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(WithdrawRequestHandler.class);

    /** Ledger service. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of withdraw request handler.
     *
     * @param ledgerService Ledger service.
     */
    public WithdrawRequestHandler(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    /** {@inheritDoc} */
    @Override public Object handle(Request request, Response response) throws Exception {
        long id = parseRequiredLongParam(request, ":id");
        long amount = parseRequiredLongQueryParam(request, "amount");

        ledgerService.withdraw(id, amount);

        log.info("Withdraw successfully processed [id=" + id + "]");

        response.status(202);
        return "";
    }
}
