package com.example.airline;

import com.example.airline.encryption.EncryptionHandler;
import com.example.airline.logging.LoggingHandler;
import com.example.airline.service.AirlineServiceImpl;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Publishes the SOAP endpoint and installs a handler chain:
 *   1. EncryptionHandler — encrypts the <price> element of each response
 *   2. LoggingHandler     — prints the SOAP message
 *
 * Outbound handlers run in list order, so encryption happens BEFORE logging:
 * the logged response is the encrypted one that goes on the wire.
 */
public class AirlinePublisher {
    public static void main(String[] args) {
        // Optional: pass a different address as the first arg, e.g.
        //   ... AirlinePublisher http://localhost:8082/airline
        String address = args.length > 0 ? args[0] : "http://localhost:8080/airline";

        Endpoint endpoint = Endpoint.create(new AirlineServiceImpl());

        List<Handler> chain = new ArrayList<>();
        chain.add(new EncryptionHandler()); // message-level encryption of <price>
        chain.add(new LoggingHandler());    // logs the (encrypted) message
        endpoint.getBinding().setHandlerChain(chain);

        endpoint.publish(address);
        System.out.println("SOAP service up - <price> is message-level encrypted.");
        System.out.println("WSDL: " + address + "?wsdl");
        System.out.println("Press Ctrl+C to stop.");
    }
}
