package com.practice.apipractice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.apipractice.model.Post;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class JsonPlaceHolderClient implements PostApiClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JsonPlaceHolderClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<Post> getPostById(int id) {
        // TODO: Implement HTTP request and JSON parsing
        URI uri = URI.create(BASE_URL + "/posts/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Post post = objectMapper.readValue(response.body(), Post.class);
                return Optional.of(post);
            } else {
                System.err.println("Request failed with status code: " + response.statusCode());
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e ) {
            System.err.println("Error fetching post by ID: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
