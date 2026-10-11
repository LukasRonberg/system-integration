package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Simple type. Generates an xsd:simpleType named "PassengerType" whose base is
 * xs:string, restricted by enumeration facets. These are the standard airline
 * passenger-type codes: ADT = adult, CHD = child, INF = infant.
 */
@XmlType(name = "PassengerType", namespace = "http://airline.example.com/")
@XmlEnum
public enum PassengerType {
    ADT,
    CHD,
    INF
}
