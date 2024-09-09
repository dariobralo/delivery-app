package com.delivery_api.service;

import com.delivery_api.persistence.model.Usuario;
import com.delivery_api.web.request.AuthCreatedUserRequest;
import com.delivery_api.web.response.AuthCreateUserResponse;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> getUsuarioById(Long id);

    Optional<Usuario> findUsuarioByEmail(String email);

    Optional<Usuario> getUsuario(String verificationToken);

    void saveRegisteredUsuario(Usuario usuario);

    void deleteUsuario(Usuario usuario);

}
