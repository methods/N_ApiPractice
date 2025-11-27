package com.practice.apipractice.model;

public record Post(
        Integer userId,
        Integer id,
        String title,
        String body
) {
}
