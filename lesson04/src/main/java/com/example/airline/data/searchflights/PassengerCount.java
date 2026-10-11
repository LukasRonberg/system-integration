package com.example.airline.data.searchflights;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/** Complex type: xsd:complexType "PassengerCount". Each field -> xs:int. */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PassengerCount", namespace = "http://airline.example.com/",
        propOrder = {"adults", "children", "infants"})
public class PassengerCount {

    private int adults;
    private int children;
    private int infants;

    public PassengerCount() { }

    public PassengerCount(int adults, int children, int infants) {
        this.adults = adults;
        this.children = children;
        this.infants = infants;
    }

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public int getInfants() { return infants; }
    public void setInfants(int infants) { this.infants = infants; }
}
