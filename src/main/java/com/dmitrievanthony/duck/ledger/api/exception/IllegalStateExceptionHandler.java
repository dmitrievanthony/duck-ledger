package com.dmitrievanthony.duck.ledger.api.exception;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Illegal state exception handler that transforms this exception into HTTP 500 status code with message.
 *
 * @see IllegalStateException
 */
public class IllegalStateExceptionHandler implements ExceptionHandler<IllegalStateException> {
    /** {@inheritDoc} */
    @Override public void handle(IllegalStateException exception, Request request, Response response) {
        response.status(500);
        response.body(exception.getMessage());
    }
}
