package com.practice.apipractice.service;

import com.practice.apipractice.model.Post;

import java.util.Optional;

public interface PostService {
    Optional<Post> createPost(Post postToCreate);
}
