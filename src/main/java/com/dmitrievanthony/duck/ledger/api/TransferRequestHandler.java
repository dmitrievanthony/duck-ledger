package com.dmitrievanthony.duck.ledger.api;

import com.dmitrievanthony.duck.ledger.core.LedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.dmitrievanthony.duck.ledger.api.util.ParamsParser.parseRequiredLongQueryParam;

/**
 * Transfer request handler.
 */
public class TransferRequestHandler implements Route {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(TransferRequestHandler.class);

    /** Ledger service. */
    private final LedgerService ledgerService;

    /**
     * Constructs a new instance of transfer request handler.
     *
     * @param ledgerService Ledger service.
     */
    public TransferRequestHandler(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    /** {@inheritDoc} */
    @Override public Object handle(Request request, Response response) throws Exception {
        long fromId = parseRequiredLongQueryParam(request, "fromId");
        long toId = parseRequiredLongQueryParam(request, "toId");
        long amount = parseRequiredLongQueryParam(request, "amount");

        ledgerService.transfer(fromId, toId, amount);

        log.info("Transfer successfully processed [from=" + fromId + ",to=" + toId + "]");

        response.status(202);
        return "";
    }
}
