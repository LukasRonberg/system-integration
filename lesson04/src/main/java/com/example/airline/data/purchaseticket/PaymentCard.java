package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Complex type: xsd:complexType "PaymentCard".
 *
 * Note: {@code number} and {@code cvv} are sensitive cardholder data. This is
 * exactly the kind of element you would want to protect with message-level
 * encryption (see the EncryptionHandler on the <price> element) rather than
 * relying on transport security alone.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCard", namespace = "http://airline.example.com/",
        propOrder = {"holderName", "number", "expiryMonth", "expiryYear", "cvv"})
public class PaymentCard {

    @XmlElement(required = true)
    private String holderName;           // -> xs:string

    @XmlElement(required = true)
    private String number;               // PAN, sensitive -> xs:string

    @XmlElement(required = true)
    private int expiryMonth;             // 1-12 -> xs:int

    @XmlElement(required = true)
    private int expiryYear;              // e.g. 2027 -> xs:int

    @XmlElement(required = true)
    private String cvv;                  // sensitive -> xs:string

    public PaymentCard() { }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public int getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(int expiryMonth) { this.expiryMonth = expiryMonth; }

    public int getExpiryYear() { return expiryYear; }
    public void setExpiryYear(int expiryYear) { this.expiryYear = expiryYear; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
