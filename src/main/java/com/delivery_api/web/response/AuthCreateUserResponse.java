package com.delivery_api.web.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "email", "roles", "message"})
public record AuthCreateUserResponse(
        String username,
        String email,
        String roles,
        String message) {
}
