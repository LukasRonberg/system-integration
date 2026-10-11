package org.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class AiApiServer {

    private static final int PORT = 8090;
    private static final String OLLAMA_URL = "http://localhost:11434/api/chat";
    private static final String DEFAULT_MODEL = "llama3.2:1b";
    private static final int MAX_PROMPT_CHARS = 8000;


    private static final String API_KEY = resolveApiKey();

    private static String resolveApiKey() {
        String fromEnv = System.getenv("API_KEY");
        return (fromEnv != null && !fromEnv.isBlank()) ? fromEnv : "dev-local-key-change-me";
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/ask", AiApiServer::handleAsk);
        server.setExecutor(null); // default single-threaded executor
        server.start();
        System.out.println("Listening on http://localhost:" + PORT + "/ask");
    }

    private static void handleAsk(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            respond(exchange, 405, error("use POST"));
            return;
        }

        // --- AUTH CHECK ---
        if (!isAuthorized(exchange)) {
            respond(exchange, 401, error("unauthorized"));
            return;
        }

        try {
            JSONObject in = new JSONObject(readBody(exchange.getRequestBody()));
            String prompt = in.getString("prompt");
            String model = in.optString("model", DEFAULT_MODEL);

            // --- INPUT GUARD ---
            if (prompt.isBlank()) {
                respond(exchange, 400, error("prompt is empty"));
                return;
            }
            if (prompt.length() > MAX_PROMPT_CHARS) {
                respond(exchange, 400, error("prompt too long (max " + MAX_PROMPT_CHARS + " chars)"));
                return;
            }

            String answer = callOllama(model, prompt);

            JSONObject out = new JSONObject();
            out.put("model", model);
            out.put("response", answer);
            respond(exchange, 200, out.toString());
        } catch (Exception e) {
            respond(exchange, 500, error(String.valueOf(e.getMessage())));
        }
    }

    private static boolean isAuthorized(HttpExchange exchange) {
        String header = exchange.getRequestHeaders().getFirst("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        String presented = header.substring("Bearer ".length()).trim();
        return constantTimeEquals(presented, API_KEY);
    }

    // Compare without leaking length/position via timing. Habit worth having for secrets.
    private static boolean constantTimeEquals(String a, String b) {
        byte[] x = a.getBytes(StandardCharsets.UTF_8);
        byte[] y = b.getBytes(StandardCharsets.UTF_8);
        if (x.length != y.length) return false;
        int diff = 0;
        for (int i = 0; i < x.length; i++) diff |= x[i] ^ y[i];
        return diff == 0;
    }

    private static String callOllama(String model, String prompt) throws IOException {
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);

        JSONObject payload = new JSONObject();
        payload.put("model", model);
        payload.put("stream", false);   // false = one JSON object back, not a token stream
        payload.put("messages", new JSONArray().put(message));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(OLLAMA_URL);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(payload.toString(), StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(post)) {
                String body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                return new JSONObject(body).getJSONObject("message").getString("content");
            }
        }
    }

    private static String readBody(InputStream in) throws IOException {
        java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
        byte[] chunk = new byte[1024];
        int n;
        while ((n = in.read(chunk)) != -1) buf.write(chunk, 0, n);
        return new String(buf.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String error(String msg) {
        return new JSONObject().put("error", msg).toString();
    }

    private static void respond(HttpExchange exchange, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
