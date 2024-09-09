package com.delivery_api.service;

import com.delivery_api.web.request.AuthCreatedUserRequest;
import com.delivery_api.web.request.AuthLoginRequest;
import com.delivery_api.web.response.AuthCreateUserResponse;
import com.delivery_api.web.response.AuthenticateResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthCreateUserResponse registroUsuario(AuthCreatedUserRequest authCreatedUserRequest) throws Exception;

    AuthenticateResponse loginUsuario(AuthLoginRequest authLoginRequest) throws Exception;

    public Authentication authenticate(String email, String password);

}
