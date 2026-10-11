package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/** Complex type: xsd:complexType "Passenger". */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Passenger", namespace = "http://airline.example.com/",
        propOrder = {"type", "name", "dob", "loyaltyNumber"})
public class Passenger {

    @XmlElement(required = true)
    private PassengerType type;          // -> PassengerType simpleType

    @XmlElement(required = true)
    private Name name;                   // -> Name complexType

    // Optional date of birth (minOccurs=0), e.g. absent for some fare types.
    @XmlElement(required = false, nillable = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar dob;    // -> xs:date

    @XmlElement(required = false)
    private String loyaltyNumber;        // optional -> xs:string (minOccurs=0)

    public Passenger() { }

    public PassengerType getType() { return type; }
    public void setType(PassengerType type) { this.type = type; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public XMLGregorianCalendar getDob() { return dob; }
    public void setDob(XMLGregorianCalendar dob) { this.dob = dob; }

    public String getLoyaltyNumber() { return loyaltyNumber; }
    public void setLoyaltyNumber(String loyaltyNumber) { this.loyaltyNumber = loyaltyNumber; }
}
