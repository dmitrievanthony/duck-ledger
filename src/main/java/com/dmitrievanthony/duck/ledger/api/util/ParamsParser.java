package com.dmitrievanthony.duck.ledger.api.util;

import spark.Request;

/**
 * Utils class that helps to parse request parameters.
 */
public class ParamsParser {
    /**
     * Parses required request parameter to long.
     *
     * @param request Request.
     * @param name Parameter name.
     * @return Long value.
     */
    public static long parseRequiredLongParam(Request request, String name) {
        String val = request.params(name);

        if (val == null)
            throw new IllegalArgumentException("Parameter is required [name=" + name + "]");

        return Long.valueOf(val);
    }

    /**
     * Parses required request query parameter to long.
     *
     * @param request Request.
     * @param name Parameter name.
     * @return Long value.
     */
    public static long parseRequiredLongQueryParam(Request request, String name) {
        String val = request.queryParams(name);

        if (val == null)
            throw new IllegalArgumentException("Query parameter is required [name=" + name + "]");

        return Long.valueOf(val);
    }
}
