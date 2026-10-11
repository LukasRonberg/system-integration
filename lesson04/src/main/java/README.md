# Airline SearchFlights — JAX-WS (contract-last)

You don't write the WSDL by hand. You write annotated Java, and the WSDL is
**generated** from it — either statically by `wsgen`, or at runtime at `?wsdl`.

## Requirements
- JDK 11+
- Jakarta XML Web Services (Metro) on the classpath. Maven coordinates:
    - `com.sun.xml.ws:jaxws-rt:4.0.2`  (runtime + `wsgen`/`Endpoint`)
- On JDK 8 the API is built in — replace every `jakarta.*` import with `javax.*`.

## Option A — generate the WSDL to a file (wsgen)
From the compiled classes directory:

    wsgen -wsdl -cp . com.example.airline.service.AirlineServiceImpl

Produces `AirlineService.wsdl` plus its schema in the current directory.

## Option B — publish and view it live
Run the publisher, then fetch the WSDL:

    java com.example.airline.AirlinePublisher
    curl http://localhost:8080/airline?wsdl

## What maps to what
| Java                                   | WSDL / XSD                                   |
|----------------------------------------|----------------------------------------------|
| `@WebService` (SEI)                    | `wsdl:portType` + `wsdl:service`/`wsdl:port` |
| `@WebMethod` SearchFlights             | `wsdl:operation` (input + output message)    |
| `SearchFlightsRequest` / `...Response` | request/response `wsdl:message` + elements   |
| `CabinClass` enum                      | `xsd:simpleType` with enumeration facets     |
| `Money`, `PassengerCount`, `Flight`    | `xsd:complexType`                            |
| `List<Flight>`                         | element with `maxOccurs="unbounded"`         |
| IATA code fields                       | `xsd:string` (no pattern — none requested)   |
