package com.example.airline.service;

import com.example.airline.data.purchaseticket.Booking;
import com.example.airline.data.purchaseticket.BookingStatus;
import com.example.airline.data.purchaseticket.Name;
import com.example.airline.data.purchaseticket.Passenger;
import com.example.airline.data.purchaseticket.PassengerType;
import com.example.airline.data.purchaseticket.Ticket;
import com.example.airline.data.purchaseticket.TicketStatus;
import com.example.airline.data.searchflights.Flight;
import com.example.airline.data.searchflights.Money;
import com.example.airline.messages.purchaseticket.PurchaseTicketRequest;
import com.example.airline.messages.purchaseticket.PurchaseTicketResponse;
import com.example.airline.messages.searchflights.SearchFlightsRequest;
import com.example.airline.messages.searchflights.SearchFlightsResponse;
import jakarta.jws.WebService;
import java.math.BigDecimal;
import javax.xml.datatype.DatatypeFactory;

/**
 * Implementation. @WebService here binds the impl to the SEI and supplies the
 * wsdl:service name ("AirlineService") and wsdl:port name ("AirlinePort").
 * wsgen / the runtime read this class to generate the WSDL.
 */
@WebService(endpointInterface = "com.example.airline.service.AirlineService",
        serviceName = "AirlineService",
        portName = "AirlinePort",
        targetNamespace = "http://airline.example.com/")
public class AirlineServiceImpl implements AirlineService {

    @Override
    public SearchFlightsResponse searchFlights(SearchFlightsRequest request) {
        // Demo logic only — a real impl would query a data source.
        SearchFlightsResponse response = new SearchFlightsResponse();
        try {
            Flight flight = new Flight();
            flight.setCarrier("SAS");
            flight.setFlightNumber("SK1234");
            flight.setDepartDateTime(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("2026-10-01T08:30:00"));
            flight.setArriveDateTime(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar("2026-10-01T11:45:00"));
            flight.setOrigin(request.getOrigin());
            flight.setDestination(request.getDestination());
            flight.setPrice(new Money(new BigDecimal("1299.00"), "DKK"));
            response.getFlights().add(flight);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public PurchaseTicketResponse purchaseTicket(PurchaseTicketRequest request) {
        // Demo logic only — a real impl would take payment and issue via a GDS.
        PurchaseTicketResponse response = new PurchaseTicketResponse();
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();

            // 1. Issue a ticket for the paid booking.
            Ticket ticket = new Ticket();
            ticket.setTicketNumber("117-2401234567");
            ticket.setStatus(TicketStatus.ISSUED);
            ticket.setIssuedAt(dtf.newXMLGregorianCalendar("2026-09-18T10:15:00"));
            response.getTickets().add(ticket);

            // 2. Build the booking (echo the request's PNR) with its passengers.
            Name name = new Name();
            name.setFirstName("Alice");
            name.setLastName("Jensen");

            Passenger adult = new Passenger();
            adult.setType(PassengerType.ADT);
            adult.setName(name);
            adult.setDob(dtf.newXMLGregorianCalendar("1990-05-14"));
            adult.setLoyaltyNumber("EB123456");

            Booking booking = new Booking();
            booking.setPnr(request.getPnr() != null ? request.getPnr() : "ABC123");
            booking.setStatus(BookingStatus.TICKETED);
            booking.getPassengers().add(adult);
            response.setBooking(booking);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


}
