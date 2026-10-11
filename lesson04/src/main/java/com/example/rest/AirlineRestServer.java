package com.example.rest;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST equivalent of {@code AirlinePublisher}. Uses the JDK's built-in
 * {@code com.sun.net.httpserver.HttpServer} so — like the SOAP publisher — it
 * needs no extra dependency and runs from a main(). Listens on 8081 so it can
 * run alongside the SOAP endpoint on 8080.
 *
 * The core contrast, one line each:
 *   SOAP:  POST /airline   + a SOAP envelope; the operation lives in the body; described by WSDL.
 *   REST:  GET  /flights?… ; the resource is the URL path, inputs are query params; no contract file.
 */
public class AirlineRestServer {

    private static final FlightSearchService SERVICE = new FlightSearchService();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        HttpContext ctx = server.createContext("/flights", AirlineRestServer::handleFlights);
        ctx.getFilters().add(new LoggingFilter()); // web filter logs every request/response
        server.setExecutor(null); // default (single-threaded) executor is fine for a demo
        server.start();
        System.out.println("REST API up. Try:");
        System.out.println("  http://localhost:8081/flights?origin=CPH&destination=LHR"
                + "&departureDate=2026-10-01&adults=1&cabin=ECONOMY");
        System.out.println("Press Ctrl+C to stop.");
    }

    private static void handleFlights(HttpExchange ex) throws IOException {
        try {
            // REST uses HTTP verbs to say what you are doing. This resource is
            // read-only, so only GET is allowed; anything else is 405.
            if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                send(ex, 405, "{\"error\":\"method not allowed, use GET\"}");
                return;
            }

            Map<String, String> q = parseQuery(ex.getRequestURI().getRawQuery());
            String origin = q.get("origin");
            String destination = q.get("destination");

            // REST reports problems with HTTP status codes. A missing required
            // parameter is a 400 — where SOAP would return a <soap:Fault>.
            if (origin == null || destination == null) {
                send(ex, 400,
                        "{\"error\":\"'origin' and 'destination' query params are required\"}");
                return;
            }

            PassengerCount passengers = new PassengerCount(
                    intParam(q, "adults", 1),
                    intParam(q, "children", 0),
                    intParam(q, "infants", 0));
            CabinClass cabin = q.containsKey("cabin")
                    ? CabinClass.valueOf(q.get("cabin").toUpperCase())
                    : CabinClass.ECONOMY;

            List<Flight> flights = SERVICE.search(origin, destination, passengers, cabin);

            // Same "SearchFlightsResponse" shape as SOAP: an object holding a
            // "flights" array — just expressed as JSON instead of an XML element.
            StringBuilder json = new StringBuilder("{\"flights\":[");
            for (int i = 0; i < flights.size(); i++) {
                if (i > 0) json.append(',');
                json.append(flights.get(i).toJson());
            }
            json.append("]}");

            send(ex, 200, json.toString());

        } catch (NumberFormatException e) {
            send(ex, 400, "{\"error\":\"'adults', 'children' and 'infants' must be integers\"}");
        } catch (IllegalArgumentException e) {
            send(ex, 400, "{\"error\":\"invalid 'cabin' value\"}");
        } catch (Exception e) {
            send(ex, 500, "{\"error\":\"internal error\"}");
        }
    }

    private static void send(HttpExchange ex, int status, String body) throws IOException {
        // Stash the response so the LoggingFilter can print it after the handler runs.
        ex.setAttribute("responseStatus", status);
        ex.setAttribute("responseBody", body);
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        // Content-Type declares the representation. Here it is JSON; the SOAP
        // side was always text/xml carrying an envelope.
        ex.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    /** Split a raw query string ("a=1&b=2") into a decoded key/value map. */
    private static Map<String, String> parseQuery(String raw) {
        Map<String, String> map = new HashMap<>();
        if (raw == null || raw.isEmpty()) return map;
        for (String pair : raw.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) continue;
            String k = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
            String v = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
            map.put(k, v);
        }
        return map;
    }

    private static int intParam(Map<String, String> q, String name, int def) {
        String v = q.get(name);
        return v == null ? def : Integer.parseInt(v);
    }
}
