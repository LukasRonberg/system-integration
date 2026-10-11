package com.example.rest;

/**
 * The same four cabins as the SOAP {@code CabinClass} enum.
 *
 * The difference is what enforces them: on the SOAP side an xsd:simpleType with
 * enumeration facets lived in the schema and validators rejected anything else.
 * Here there is no schema document — these are just JSON string values the two
 * sides agree on out-of-band (an OpenAPI spec, or plain docs). REST has no
 * contract file that a toolkit downloads and validates against.
 */
public enum CabinClass {
    ECONOMY,
    PREMIUM_ECONOMY,
    BUSINESS,
    FIRST
}
