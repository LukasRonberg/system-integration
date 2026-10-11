# Airline SearchFlights — REST (the same data, no WSDL)

This package returns the **exact same flight information** as the SOAP service in
`com.example.airline`, but as a plain HTTP+JSON REST API. It uses only the JDK's
built-in `com.sun.net.httpserver.HttpServer` — no extra dependency — mirroring how
the SOAP side used the built-in `Endpoint.publish`.

## Run it

    mvn -q compile exec:java -Dexec.mainClass=com.example.rest.AirlineRestServer

(The `-Dexec.mainClass=...` overrides the publisher configured in `pom.xml`, so no
build change is needed. It listens on **8081**, so it can run at the same time as
the SOAP endpoint on 8080.)

Then call it:

    curl "http://localhost:8081/flights?origin=CPH&destination=LHR&departureDate=2026-10-01&adults=1&cabin=ECONOMY"

Response:

    {"flights":[{"carrier":"SAS","flightNumber":"SK1234",
     "departDateTime":"2026-10-01T08:30:00","arriveDateTime":"2026-10-01T11:45:00",
     "origin":"CPH","destination":"LHR","price":{"amount":1299.00,"currency":"DKK"}}]}

## SOAP vs REST — same operation, different model

| Concern            | SOAP (`com.example.airline`)                  | REST (`com.example.rest`)                     |
|--------------------|-----------------------------------------------|-----------------------------------------------|
| Where the op lives | `SearchFlights` operation in the SOAP body    | HTTP verb + path: `GET /flights`              |
| Inputs             | XML element in an envelope                     | query params `?origin=…&destination=…`        |
| Output format      | `text/xml` SOAP envelope                       | `application/json`                            |
| Contract           | **WSDL** (+ XSD), machine-generated & downloadable | none built in; describe with **OpenAPI** if wanted |
| Types / validation | XSD (enumerations, minOccurs, dateTime, …)     | convention; JSON has no schema unless you add one |
| Errors             | `<soap:Fault>` (HTTP 200/500)                  | HTTP status codes (400 / 404 / 405 / 500)     |
| Transport          | usually HTTP POST only                          | any HTTP verb; GET is cacheable               |
| Tooling to consume | `wsimport` generates a typed client from WSDL  | any HTTP client; codegen only if OpenAPI given |

## Files
- `AirlineRestServer` — the HTTP server + routing (parallel to `AirlinePublisher`)
- `FlightSearchService` — the search logic (parallel to `AirlineServiceImpl`)
- `Flight`, `Money`, `PassengerCount`, `CabinClass` — the data (records, no JAXB)
- `Json` — a tiny hand-rolled serializer (a real app would use Jackson/JAX-RS)
