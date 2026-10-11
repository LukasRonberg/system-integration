package com.example.airline.logging;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Logs the SOAP message on the way in and out — the SOAP analogue of a servlet
 * web filter. In the handler chain it sits AFTER the EncryptionHandler, and
 * outbound handlers run top-to-bottom, so what this prints for the response is
 * the ENCRYPTED envelope that actually goes on the wire.
 */
public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext ctx) {
        boolean outbound = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        System.out.println(outbound
                ? "\n===== SOAP >>> OUTBOUND response (on the wire) ====="
                : "\n===== SOAP <<< INBOUND request =====");
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ctx.getMessage().writeTo(out);
            System.out.println(out.toString(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("(could not serialize message: " + e.getMessage() + ")");
        }
        return true;
    }

    @Override public boolean handleFault(SOAPMessageContext ctx) { return handleMessage(ctx); }
    @Override public void close(MessageContext ctx) { }
    @Override public Set<QName> getHeaders() { return null; }
}
