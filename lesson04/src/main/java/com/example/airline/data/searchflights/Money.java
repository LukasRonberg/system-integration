package com.example.airline.data.searchflights;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/** Complex type: xsd:complexType "Money". */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Money", namespace = "http://airline.example.com/",
        propOrder = {"amount", "currency"})
public class Money {

    @XmlElement(required = true)
    private BigDecimal amount;      // -> xs:decimal

    @XmlElement(required = true)
    private String currency;        // ISO 4217, e.g. "DKK", "EUR" -> xs:string

    public Money() { }

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
