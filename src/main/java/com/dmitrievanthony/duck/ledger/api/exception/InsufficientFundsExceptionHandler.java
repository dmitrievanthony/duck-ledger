package com.dmitrievanthony.duck.ledger.api.exception;

import com.dmitrievanthony.duck.ledger.core.exception.InsufficientFundsException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Insufficient funds exception handler that transforms this exception into HTTP 400 status and message.
 *
 * @see InsufficientFundsException
 */
public class InsufficientFundsExceptionHandler implements ExceptionHandler<InsufficientFundsException> {
    /** {@inheritDoc} */
    @Override public void handle(InsufficientFundsException exception, Request request, Response response) {
        response.status(400);
        response.body(exception.getMessage());
    }
}
