package com.example.airline.encryption;

import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Set;

/**
 * A JAX-WS SOAP handler that performs MESSAGE-LEVEL encryption on the response.
 *
 * Handlers are the SOAP interceptor mechanism — the same extension point that
 * WS-Security uses. On the outbound response we find the <price> element and
 * replace its children (<amount>, <currency>) with an <xenc:EncryptedData>
 * element holding the ciphertext. Only that one element is encrypted; the rest
 * of the message (carrier, route, times) stays readable. That "encrypt just the
 * sensitive part" is something transport encryption (TLS) cannot do.
 */
public class EncryptionHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String XENC = "http://www.w3.org/2001/04/xmlenc#";

    @Override
    public boolean handleMessage(SOAPMessageContext ctx) {
        boolean outbound = (Boolean) ctx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (!outbound) return true; // only encrypt the server's RESPONSE

        try {
            SOAPMessage msg = ctx.getMessage();
            SOAPBody body = msg.getSOAPPart().getEnvelope().getBody();

            NodeList prices = body.getElementsByTagNameNS("*", "price");
            for (int i = 0; i < prices.getLength(); i++) {
                Element price = (Element) prices.item(i);

                // 1. serialize the current content: "<amount>..</amount><currency>..</currency>"
                String plaintext = innerXml(price);

                // 2. encrypt it
                String cipher = Crypto.encrypt(plaintext);

                // 3. swap the children for a single <xenc:EncryptedData> node
                while (price.getFirstChild() != null) {
                    price.removeChild(price.getFirstChild());
                }
                Element enc = price.getOwnerDocument()
                        .createElementNS(XENC, "xenc:EncryptedData");
                enc.setAttribute("Type", XENC + "Content");
                enc.setTextContent(cipher);
                price.appendChild(enc);
            }
            msg.saveChanges();
        } catch (Exception e) {
            throw new RuntimeException("SOAP encryption failed", e);
        }
        return true;
    }

    /** Serialize the child nodes of an element to an XML string. */
    private static String innerXml(Node node) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringBuilder sb = new StringBuilder();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            StringWriter sw = new StringWriter();
            t.transform(new DOMSource(children.item(i)), new StreamResult(sw));
            sb.append(sw);
        }
        return sb.toString();
    }

    @Override public boolean handleFault(SOAPMessageContext ctx) { return true; }
    @Override public void close(MessageContext ctx) { }
    @Override public Set<QName> getHeaders() { return null; }
}
