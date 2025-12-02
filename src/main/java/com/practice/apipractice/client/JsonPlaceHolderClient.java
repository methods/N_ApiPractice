package com.practice.apipractice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Override
    public Optional<Post> createPost(Post postToCreate) {
        try {
            String jsonPost = objectMapper.writeValueAsString(postToCreate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/posts/"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPost))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                Post post = objectMapper.readValue(response.body(), Post.class);
                return Optional.of(post);
            } else {
                System.err.println("Request failed with status code: " + response.statusCode() + " and body: " + response.body());
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e ) {
            System.err.println("Error creating Post: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
