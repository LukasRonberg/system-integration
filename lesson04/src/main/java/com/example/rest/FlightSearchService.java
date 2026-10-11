package com.example.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The same demo logic as {@code AirlineServiceImpl.searchFlights}: one
 * hard-coded SAS flight that echoes the requested origin/destination.
 *
 * Note what is NOT here compared to the SOAP version: no @WebService, no
 * endpoint interface / "contract" class, nothing that a tool reads to generate
 * a WSDL. The "interface" of a REST service is the set of URLs and HTTP verbs
 * it answers — described, if at all, by OpenAPI, not by a generated document.
 */
public class FlightSearchService {

    public List<Flight> search(String origin, String destination,
                               PassengerCount passengers, CabinClass cabin) {
        // Demo logic only — a real impl would query a data source.
        // passengers/cabin are accepted but unused here, exactly as in the SOAP impl.
        Flight f = new Flight(
                "SAS",
                "SK1234",
                LocalDateTime.parse("2026-10-01T08:30:00"),
                LocalDateTime.parse("2026-10-01T11:45:00"),
                origin,
                destination,
                new Money(new BigDecimal("1299.00"), "DKK"));
        return List.of(f);
    }
}
