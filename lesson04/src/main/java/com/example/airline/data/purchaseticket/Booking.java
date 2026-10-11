package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Complex type: xsd:complexType "Booking". The List<Passenger> becomes a
 * repeated <passenger> element (minOccurs=0, maxOccurs="unbounded").
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Booking", namespace = "http://airline.example.com/",
        propOrder = {"pnr", "status", "passengers"})
public class Booking {

    @XmlElement(required = true)
    private String pnr;                  // -> xs:string

    @XmlElement(required = true)
    private BookingStatus status;        // -> BookingStatus simpleType

    @XmlElement(name = "passenger")
    private List<Passenger> passengers = new ArrayList<>();

    public Booking() { }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}
