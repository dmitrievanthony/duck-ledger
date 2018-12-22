package com.dmitrievanthony.duck.ledger.api.exception;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Illegal argument exception handler that transforms this exception into HTTP 400 status code with message.
 *
 * @see IllegalArgumentException
 */
public class IllegalArgumentExceptionHandler implements ExceptionHandler<IllegalArgumentException> {
    /** {@inheritDoc} */
    @Override public void handle(IllegalArgumentException exception, Request request, Response response) {
        response.status(400);
        response.body(exception.getMessage());
    }
}
