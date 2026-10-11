package com.example.rest;

/**
 * A tiny JSON helper so this module stays dependency-free, exactly like the
 * SOAP side used only the built-in Endpoint/JAXB. A production REST service
 * would NOT hand-roll JSON — it would bind objects with Jackson
 * (com.fasterxml.jackson) or a JAX-RS provider. This is kept manual only to
 * make the "REST is just HTTP + text" point without adding a framework.
 */
final class Json {
    private Json() { }

    /** Quote and escape a string as a JSON string literal (or {@code null}). */
    static String q(String s) {
        if (s == null) return "null";
        StringBuilder b = new StringBuilder(s.length() + 2).append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> b.append("\\\"");
                case '\\' -> b.append("\\\\");
                case '\n' -> b.append("\\n");
                case '\r' -> b.append("\\r");
                case '\t' -> b.append("\\t");
                default   -> {
                    if (c < 0x20) b.append(String.format("\\u%04x", (int) c));
                    else b.append(c);
                }
            }
        }
        return b.append('"').toString();
    }
}
