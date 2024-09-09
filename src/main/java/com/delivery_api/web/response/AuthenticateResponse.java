package com.delivery_api.web.response;

public record AuthenticateResponse(
        String email,
        String roles,
        String jwt) {
}
