package com.example.airline.messages.searchflights;

import com.example.airline.data.searchflights.CabinClass;
import com.example.airline.data.searchflights.PassengerCount;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Input message. With DOCUMENT/LITERAL BARE binding this becomes the single
 * part of the SearchFlights request message, backed by a global element.
 */
@XmlRootElement(name = "SearchFlightsRequest", namespace = "http://airline.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchFlightsRequest", namespace = "http://airline.example.com/",
        propOrder = {"origin", "destination", "departureDate",
                "returnDate", "passengers", "cabin"})
public class SearchFlightsRequest {

    @XmlElement(required = true)
    private String origin;               // IataCode -> xs:string

    @XmlElement(required = true)
    private String destination;          // IataCode -> xs:string

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar departureDate;   // -> xs:date

    // Optional: absent for a one-way search, present for a round trip.
    @XmlElement(required = false, nillable = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar returnDate;      // -> xs:date (minOccurs=0)

    @XmlElement(required = true)
    private PassengerCount passengers;

    @XmlElement(required = true)
    private CabinClass cabin;

    public SearchFlightsRequest() { }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public XMLGregorianCalendar getDepartureDate() { return departureDate; }
    public void setDepartureDate(XMLGregorianCalendar v) { this.departureDate = v; }

    public XMLGregorianCalendar getReturnDate() { return returnDate; }
    public void setReturnDate(XMLGregorianCalendar v) { this.returnDate = v; }

    public PassengerCount getPassengers() { return passengers; }
    public void setPassengers(PassengerCount passengers) { this.passengers = passengers; }

    public CabinClass getCabin() { return cabin; }
    public void setCabin(CabinClass cabin) { this.cabin = cabin; }
}
