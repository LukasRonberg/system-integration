package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/** Complex type: xsd:complexType "Ticket". */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ticket", namespace = "http://airline.example.com/",
        propOrder = {"ticketNumber", "status", "issuedAt"})
public class Ticket {

    @XmlElement(required = true)
    private String ticketNumber;         // -> xs:string

    @XmlElement(required = true)
    private TicketStatus status;         // -> TicketStatus simpleType

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar issuedAt;   // -> xs:dateTime

    public Ticket() { }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public XMLGregorianCalendar getIssuedAt() { return issuedAt; }
    public void setIssuedAt(XMLGregorianCalendar issuedAt) { this.issuedAt = issuedAt; }
}
