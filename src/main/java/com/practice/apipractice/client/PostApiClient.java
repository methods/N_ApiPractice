package com.practice.apipractice.client;

import com.practice.apipractice.model.Post;

import java.util.Optional;

public interface PostApiClient {
    Optional<Post> getPostById(int id);
}
