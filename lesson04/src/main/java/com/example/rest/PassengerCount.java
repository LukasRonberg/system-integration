package com.example.rest;

/**
 * Mirrors the SOAP PassengerCount complexType. On the REST side this is read
 * from query parameters (?adults=1&children=0&infants=0) rather than from an
 * XML element nested in a SOAP body.
 */
public record PassengerCount(int adults, int children, int infants) { }
