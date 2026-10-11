package com.example.airline.messages.purchaseticket;

import com.example.airline.data.purchaseticket.PaymentInfo;
import jakarta.xml.bind.annotation.*;

/**
 * Input message. With DOCUMENT/LITERAL BARE binding this becomes the single
 * part of the PurchaseTicket request message, backed by a global element.
 */
@XmlRootElement(name = "PurchaseTicketRequest", namespace = "http://airline.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseTicketRequest", namespace = "http://airline.example.com/",
        propOrder = {"pnr", "paymentInfo", "billingEmail"})
public class PurchaseTicketRequest {

    @XmlElement(required = true)
    private String pnr;

    @XmlElement(name = "paymentInfo")
    private PaymentInfo paymentInfo;

    @XmlElement(required = true)
    private String billingEmail;          // IataCode -> xs:string

    public PurchaseTicketRequest() { }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }

    public PaymentInfo getPayment() { return paymentInfo; }
    public void setPaymentInfo(PaymentInfo paymentInfo) { this.paymentInfo = paymentInfo; }

    public String getBillingEmail() { return billingEmail; }
    public void setBillingEmail(String billingEmail) { this.billingEmail = billingEmail; }
}
