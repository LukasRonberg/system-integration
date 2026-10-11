package com.example.airline.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Minimal AES-GCM helper used to demonstrate MESSAGE-LEVEL encryption.
 *
 * This is a teaching stand-in for WS-Security XML-Encryption. Real WSS uses
 * public-key crypto with X.509 certificates and a randomly generated per-message
 * symmetric key (wrapped for the recipient); here we hard-code one shared 128-bit
 * key so the demo runs with no keystore. The IMPORTANT part is the same either
 * way: plaintext element content is replaced by ciphertext that travels INSIDE
 * the message, so it stays protected even over plain HTTP and through any
 * intermediary that forwards the SOAP body.
 */
public final class Crypto {

    // DEMO ONLY — never hard-code a key in real code.
    private static final byte[] KEY = "0123456789abcdef".getBytes(StandardCharsets.UTF_8);
    private static final SecureRandom RNG = new SecureRandom();

    private Crypto() { }

    /** Encrypt UTF-8 text; output is base64(iv || ciphertext+tag). */
    public static String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[12];
            RNG.nextBytes(iv);
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY, "AES"), new GCMParameterSpec(128, iv));
            byte[] ct = c.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            byte[] out = new byte[iv.length + ct.length];
            System.arraycopy(iv, 0, out, 0, iv.length);
            System.arraycopy(ct, 0, out, iv.length, ct.length);
            return Base64.getEncoder().encodeToString(out);
        } catch (Exception e) {
            throw new RuntimeException("encrypt failed", e);
        }
    }

    /** Reverse of {@link #encrypt} — proves the ciphertext is recoverable. */
    public static String decrypt(String base64) {
        try {
            byte[] in = Base64.getDecoder().decode(base64);
            byte[] iv = Arrays.copyOfRange(in, 0, 12);
            byte[] ct = Arrays.copyOfRange(in, 12, in.length);
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY, "AES"), new GCMParameterSpec(128, iv));
            return new String(c.doFinal(ct), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("decrypt failed", e);
        }
    }
}
