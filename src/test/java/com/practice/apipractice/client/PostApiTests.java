package com.practice.apipractice.client;

import com.practice.apipractice.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PostApiTests {

    private PostApiClient postApiClient;

    @BeforeEach
    void setup() {
        postApiClient = new JsonPlaceHolderClient();
    }

    @Test
    void getPostById_WithValidId_ReturnsPost () {
        // GIVEN a Post Id that is known to exist
        int postId = 1;

        // WHEN the client is called with the Post Id
        Optional<Post> result = postApiClient.getPostById(postId);

        // THEN the optional should be present
        assertThat(result).isPresent();

        // AND the Post object fields should be correct
        Post post = result.get();
        assertThat(post.id()).isEqualTo(postId);
        assertThat(post.userId()).isNotNull().isPositive();
        assertThat(post.title()).isNotBlank();
        assertThat(post.body()).isNotBlank();
    }

    @Test
    void getPostById_WithInvalidId_ReturnsEmpty() {
        // GIVEN a Post Id that is known to be invalid
        int invalidId = -99;

        // WHEN the client is called with the Post Id
        Optional<Post> result = postApiClient.getPostById(invalidId);

        // THEN the API should return 404 which the client should return as an empty Optional
        assertThat(result).isEmpty();
    }
}
