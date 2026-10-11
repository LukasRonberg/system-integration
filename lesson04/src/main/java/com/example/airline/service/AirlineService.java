package com.example.airline.service;

import com.example.airline.messages.purchaseticket.PurchaseTicketRequest;
import com.example.airline.messages.purchaseticket.PurchaseTicketResponse;
import com.example.airline.messages.searchflights.SearchFlightsRequest;
import com.example.airline.messages.searchflights.SearchFlightsResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * Service Endpoint Interface (the "contract").
 *
 * @WebService              -> wsdl:portType "AirlinePortType"
 * @WebMethod               -> wsdl:operation "SearchFlights"
 * DOCUMENT / LITERAL / BARE -> each message has exactly one part, which is the
 *                             request/response element defined by the JAXB beans.
 */
@WebService(name = "AirlinePortType", targetNamespace = "http://airline.example.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT,
        use = SOAPBinding.Use.LITERAL,
        parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface AirlineService {

    @WebMethod(operationName = "SearchFlights",
            action = "http://airline.example.com/SearchFlights")
    @WebResult(name = "SearchFlightsResponse",
            targetNamespace = "http://airline.example.com/")
    SearchFlightsResponse searchFlights(
            @WebParam(name = "SearchFlightsRequest",
                    targetNamespace = "http://airline.example.com/")
            SearchFlightsRequest request);

    @WebMethod(operationName = "PurchaseTicket",
            action = "http://airline.example.com/PurchaseTicket")
    @WebResult(name = "PurchaseTicketResponse",
            targetNamespace = "http://airline.example.com/")
    PurchaseTicketResponse purchaseTicket(
            @WebParam(name = "PurchaseTicketRequest",
                    targetNamespace = "http://airline.example.com/")
            PurchaseTicketRequest request);
}
