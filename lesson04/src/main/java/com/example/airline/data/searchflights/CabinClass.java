package com.example.airline.data.searchflights;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Simple type. Generates an xsd:simpleType named "CabinClass" whose base is
 * xs:string, restricted by enumeration facets (one per constant below).
 */
@XmlType(name = "CabinClass", namespace = "http://airline.example.com/")
@XmlEnum
public enum CabinClass {
    ECONOMY,
    PREMIUM_ECONOMY,
    BUSINESS,
    FIRST
}
