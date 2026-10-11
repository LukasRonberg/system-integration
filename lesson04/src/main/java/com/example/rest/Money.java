package com.example.rest;

import java.math.BigDecimal;

/**
 * Same shape as the SOAP "Money" complexType, but a plain Java record with no
 * JAXB/XML annotations. BigDecimal keeps its scale, so 1299.00 stays 1299.00.
 */
public record Money(BigDecimal amount, String currency) {

    /** Serialize as a JSON object. {@code amount} is a JSON number, not a string. */
    public String toJson() {
        return "{\"amount\":" + amount + ",\"currency\":" + Json.q(currency) + "}";
    }
}
