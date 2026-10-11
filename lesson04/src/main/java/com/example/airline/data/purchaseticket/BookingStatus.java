package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Simple type. Generates an xsd:simpleType named "BookingStatus" whose base is
 * xs:string, restricted by enumeration facets (one per constant below).
 */
@XmlType(name = "BookingStatus", namespace = "http://airline.example.com/")
@XmlEnum
public enum BookingStatus {
    ON_HOLD,
    TICKETED,
    CANCELLED
}
