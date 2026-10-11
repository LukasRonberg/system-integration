package com.example.airline.data.purchaseticket;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Complex type: xsd:complexType "PaymentInfo".
 *
 * The spec lists amount + currency as two separate fields (not a Money type),
 * so they are kept separate here. {@code card} is present only when
 * {@code method} is CARD, hence optional (minOccurs=0).
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentInfo", namespace = "http://airline.example.com/",
        propOrder = {"method", "card", "amount", "currency"})
public class PaymentInfo {

    @XmlElement(required = true)
    private PaymentMethod method;        // -> PaymentMethod simpleType

    // Optional: only for CARD payments. Absent for WALLET / BANK_TRANSFER.
    @XmlElement(required = false)
    private PaymentCard card;            // -> PaymentCard complexType (minOccurs=0)

    @XmlElement(required = true)
    private BigDecimal amount;           // -> xs:decimal

    // CurrencyCode is modelled as a plain ISO-4217 xs:string, matching Money.currency.
    @XmlElement(required = true)
    private String currency;             // CurrencyCode (ISO 4217) -> xs:string

    public PaymentInfo() { }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

    public PaymentCard getCard() { return card; }
    public void setCard(PaymentCard card) { this.card = card; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
