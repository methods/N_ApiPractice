package com.practice.apipractice.service;

import com.practice.apipractice.client.PostApiClient;
import com.practice.apipractice.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceUnitTests {

    @Mock
    private PostApiClient mockPostApiClient;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void createPost_WhenClientSucceeds_ReturnsCreatedPost() {
        // GIVEN a valid post to create with no Id field
        Post postToCreate = new Post(1, null, "Test Title", "Test Body");

        // AND the expected post returned from the API (now with Id)
        Post expectedPostFromApi = new Post(1, 101, "Test Title", "Test Body");

        // AND a mock client configured to return the expected Post when called appropriately
        when(mockPostApiClient.createPost(postToCreate)).thenReturn(Optional.of(expectedPostFromApi));

        // WHEN the service method is called
        Optional<Post> result = postService.createPost(postToCreate);

        // THEN the service should call the client once
        verify(mockPostApiClient).createPost(postToCreate);

        // AND the Post should be returned correctly
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedPostFromApi);

    }
}
