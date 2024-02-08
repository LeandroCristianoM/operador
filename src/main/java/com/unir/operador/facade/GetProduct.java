package com.unir.operador.facade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.unir.operador.model.responseProduct.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetProduct {
    @Value("${getProduct.url}")
    private String getProductUrl;

    private final RestTemplate restTemplate;

    public ResponseProduct getOrden(String id) {

        HttpClient client = HttpClient.newHttpClient();

        // Define the URL you want to send the request to
        URI uri = URI.create(getProductUrl + Integer.parseInt(id));

        // Create an HttpRequest with the desired method (GET in this case)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ResponseProduct posts = mapper.readValue(response.body(), new TypeReference<ResponseProduct>() {});

            return posts;
        } catch (Exception e) {
            log.error("Client Error: {}, Product with ID {}", e.getMessage(), id);
            return null;
        }
    }

    public boolean updOrden(String id, ResponseProduct product) throws IOException, InterruptedException {
        Gson gson = new Gson();
        String json = gson.toJson(product);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getProductUrl + Integer.parseInt(id)))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return true;

        }
        return false;
    }
}

