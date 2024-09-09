package com.delivery_api.web.request;

import com.delivery_api.validation.PasswordMatches;
import com.delivery_api.validation.ValidEmail;
import jakarta.validation.constraints.*;

@PasswordMatches
public record AuthCreatedUserRequest(
        @NotBlank(message = "El campo no puede estar vacío")
        @ValidEmail String email,

        @NotBlank(message = "El campo no puede estar vacío")
        @Size(min = 3, max = 25, message = "El nombre de usuario debe tener entre 3 y 25 caracteres") String username,

        @NotBlank @Size(max = 20) String telefono,

        @NotBlank(message = "El campo no puede estar vacío")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{10,}$",
                message = "La Contraseña debe tener mayúsculas, minúsculas y un mínimo de 10 caracteres.") String password,

        @NotBlank String matchingPassword,

        @NotBlank(message = "El campo no puede estar vacío") String role
        ) {
}
