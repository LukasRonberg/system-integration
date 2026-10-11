package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Complex type: xsd:complexType "Name".
 *
 * NOTE: the spec references Passenger.name as type "Name" but does not define
 * its fields. Chosen here: firstName + lastName (required), middleName optional
 * — enough to render the "Full name" the client prints. Adjust to taste
 * (e.g. add a title, or split into given/family) if your spec means otherwise.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Name", namespace = "http://airline.example.com/",
        propOrder = {"firstName", "middleName", "lastName"})
public class Name {

    @XmlElement(required = true)
    private String firstName;            // -> xs:string

    @XmlElement(required = false)
    private String middleName;           // optional -> xs:string (minOccurs=0)

    @XmlElement(required = true)
    private String lastName;             // -> xs:string

    public Name() { }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    /** Convenience for the client's "Full name" output. */
    public String getFullName() {
        return (middleName == null || middleName.isBlank())
                ? firstName + " " + lastName
                : firstName + " " + middleName + " " + lastName;
    }
}
