package com.example.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Same fields as the SOAP "Flight" complexType. Where the SOAP bean used
 * XMLGregorianCalendar mapped to xs:dateTime, here we use LocalDateTime and
 * emit an ISO-8601 string in the JSON — same value ("2026-10-01T08:30:00"),
 * no schema type to declare.
 */
public record Flight(
        String carrier,
        String flightNumber,
        LocalDateTime departDateTime,
        LocalDateTime arriveDateTime,
        String origin,
        String destination,
        Money price) {

    private static final DateTimeFormatter ISO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public String toJson() {
        return "{"
                + "\"carrier\":" + Json.q(carrier) + ","
                + "\"flightNumber\":" + Json.q(flightNumber) + ","
                + "\"departDateTime\":" + Json.q(departDateTime.format(ISO)) + ","
                + "\"arriveDateTime\":" + Json.q(arriveDateTime.format(ISO)) + ","
                + "\"origin\":" + Json.q(origin) + ","
                + "\"destination\":" + Json.q(destination) + ","
                + "\"price\":" + price.toJson()
                + "}";
    }
}
