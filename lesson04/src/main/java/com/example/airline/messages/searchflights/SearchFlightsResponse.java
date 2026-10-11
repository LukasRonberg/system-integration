package com.example.airline.messages.searchflights;

import com.example.airline.data.searchflights.Flight;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Output message. The List<Flight> becomes a repeated element
 * (minOccurs=0, maxOccurs="unbounded") -> "list of Flight".
 */
@XmlRootElement(name = "SearchFlightsResponse", namespace = "http://airline.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchFlightsResponse", namespace = "http://airline.example.com/",
        propOrder = {"flights"})
public class SearchFlightsResponse {

    @XmlElement(name = "flight")
    private List<Flight> flights = new ArrayList<>();

    public SearchFlightsResponse() { }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}
