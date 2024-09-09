package com.delivery_api.web.controllers;

import com.delivery_api.service.AuthService;
import com.delivery_api.utils.jwt.JwtUtilService;
import com.delivery_api.web.request.AuthCreatedUserRequest;
import com.delivery_api.web.request.AuthLoginRequest;
import com.delivery_api.web.response.AuthCreateUserResponse;
import com.delivery_api.web.response.AuthenticateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;

    public AuthController(UserDetailsService userDetailsService,
                          AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtUtilService jwtUtilService) {
        this.userDetailsService = userDetailsService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
    }

    /*
     *
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthCreateUserResponse> register(
            @RequestBody @Valid AuthCreatedUserRequest authCreatedUserRequest) throws Exception {
        AuthCreateUserResponse nuevoUsuario = authService.registroUsuario(authCreatedUserRequest);

        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/singin")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest loginRequest) throws Exception {
        AuthenticateResponse authenticateResponse = authService.loginUsuario(loginRequest);
        if(authenticateResponse == null){
            return new ResponseEntity<>("Fallo ingreso de sesion. Revise los datos.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(authenticateResponse, HttpStatus.ACCEPTED);
    }

}
