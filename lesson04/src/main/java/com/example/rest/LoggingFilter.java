package com.example.rest;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * A web filter (com.sun.net.httpserver.Filter) that logs each request and its
 * response — the REST analogue of the SOAP LoggingHandler.
 *
 * The response body is plaintext JSON. There is no message-level encryption
 * here: REST relies on TLS (HTTPS) to protect the whole channel, so the payload
 * itself is in the clear. Run this next to the SOAP log to see the difference —
 * the SOAP <price> is ciphertext, this JSON price is readable.
 */
public class LoggingFilter extends Filter {

    @Override
    public String description() {
        return "request/response logger";
    }

    @Override
    public void doFilter(HttpExchange ex, Chain chain) throws IOException {
        System.out.println("\n===== REST <<< INBOUND request =====");
        System.out.println(ex.getRequestMethod() + " " + ex.getRequestURI()
                + "   from " + ex.getRemoteAddress());

        chain.doFilter(ex); // run the actual handler

        // The handler stashes what it sent so we can log it here.
        Object status = ex.getAttribute("responseStatus");
        Object body = ex.getAttribute("responseBody");
        System.out.println("===== REST >>> OUTBOUND response (on the wire) =====");
        System.out.println(status + "  " + body);
    }
}
