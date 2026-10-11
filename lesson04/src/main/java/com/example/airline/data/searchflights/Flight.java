package com.example.airline.data.searchflights;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/** Complex type: xsd:complexType "Flight". */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Flight", namespace = "http://airline.example.com/",
        propOrder = {"carrier", "flightNumber", "departDateTime",
                "arriveDateTime", "origin", "destination", "price"})
public class Flight {

    @XmlElement(required = true)
    private String carrier;              // airline name -> xs:string

    @XmlElement(required = true)
    private String flightNumber;         // alphanumeric -> xs:string (no pattern)

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar departDateTime;   // -> xs:dateTime

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar arriveDateTime;   // -> xs:dateTime

    // IataCode is a plain xs:string here (no regex/pattern restriction requested).
    @XmlElement(required = true)
    private String origin;               // IataCode -> xs:string

    @XmlElement(required = true)
    private String destination;          // IataCode -> xs:string

    @XmlElement(required = true)
    private Money price;                 // -> Money complexType

    public Flight() { }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public XMLGregorianCalendar getDepartDateTime() { return departDateTime; }
    public void setDepartDateTime(XMLGregorianCalendar v) { this.departDateTime = v; }

    public XMLGregorianCalendar getArriveDateTime() { return arriveDateTime; }
    public void setArriveDateTime(XMLGregorianCalendar v) { this.arriveDateTime = v; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Money getPrice() { return price; }
    public void setPrice(Money price) { this.price = price; }
}
