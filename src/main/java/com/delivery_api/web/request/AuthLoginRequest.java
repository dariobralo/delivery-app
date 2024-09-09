package com.delivery_api.web.request;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank String email,
        @NotBlank String password) {
}
