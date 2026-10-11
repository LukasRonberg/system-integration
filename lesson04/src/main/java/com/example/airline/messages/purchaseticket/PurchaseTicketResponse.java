package com.example.airline.messages.purchaseticket;

import com.example.airline.data.purchaseticket.Booking;
import com.example.airline.data.purchaseticket.Ticket;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Output message. The List<Flight> becomes a repeated element
 * (minOccurs=0, maxOccurs="unbounded") -> "list of Flight".
 */
@XmlRootElement(name = "PurchaseTicketResponse", namespace = "http://airline.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseTicketResponse", namespace = "http://airline.example.com/",
        propOrder = {"tickets", "booking"})
public class PurchaseTicketResponse {

    @XmlElement(name = "ticket")
    private List<Ticket> tickets = new ArrayList<>();

    @XmlElement(name="booking")
    private Booking booking;

    public PurchaseTicketResponse() { }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
