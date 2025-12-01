package com.practice.apipractice.service;

import com.practice.apipractice.client.PostApiClient;
import com.practice.apipractice.model.Post;

import java.util.Optional;

public class PostServiceImpl implements PostService {

    private final PostApiClient postApiClient;

    public PostServiceImpl(PostApiClient postApiClient) {
        this.postApiClient = postApiClient;
    }

    @Override
    public Optional<Post> createPost(Post postToCreate) {
        String title = postToCreate.title();

        if ( title == null || title.isBlank()) {
            return Optional.empty();
        }
        return postApiClient.createPost(postToCreate);
    };
}
