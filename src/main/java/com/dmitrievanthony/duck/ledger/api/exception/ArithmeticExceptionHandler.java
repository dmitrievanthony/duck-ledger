package com.dmitrievanthony.duck.ledger.api.exception;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Arithmetical exception handler. In particular handles numeric overflows.
 *
 * @see ArithmeticException
 */
public class ArithmeticExceptionHandler implements ExceptionHandler<ArithmeticException> {
    /** {@inheritDoc} */
    @Override public void handle(ArithmeticException exception, Request request, Response response) {
        response.status(400);
        response.body("Operation cannot be performed due to system limitations");
    }
}
