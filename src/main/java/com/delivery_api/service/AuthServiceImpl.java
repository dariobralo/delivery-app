package com.delivery_api.service;

import com.delivery_api.persistence.dao.RolRepository;
import com.delivery_api.persistence.dao.UsuarioRepository;
import com.delivery_api.persistence.model.Rol;
import com.delivery_api.persistence.model.Usuario;
import com.delivery_api.utils.jwt.JwtUtilService;
import com.delivery_api.web.request.AuthCreatedUserRequest;
import com.delivery_api.web.request.AuthLoginRequest;
import com.delivery_api.web.response.AuthCreateUserResponse;
import com.delivery_api.web.response.AuthenticateResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtilService jwtUtilService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           RolRepository rolRepository,
                           UserDetailsService userDetailsService,
                           JwtUtilService jwtUtilService,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.userDetailsService = userDetailsService;
        this.jwtUtilService = jwtUtilService;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     *
     */
    @Override
    public AuthCreateUserResponse registroUsuario(AuthCreatedUserRequest authCreatedUserRequest) throws Exception {
        if (emailExists(authCreatedUserRequest.email())){
            throw new Exception("El email no esta disponible");
        }
        Rol rol = rolRepository.findRoleByString(authCreatedUserRequest.role())
                .orElseThrow(() -> new RuntimeException("El rol ingresado no esta disponible"));
        Usuario nuevoUsuario = Usuario.builder()
                .username(authCreatedUserRequest.username())
                .email(authCreatedUserRequest.email())
                .password(passwordEncoder.encode(authCreatedUserRequest.password()))
                .listRols(List.of(rol))
                .isEnabled(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .accountNoLocked(true)
                .build();
        usuarioRepository.save(nuevoUsuario);

        return new AuthCreateUserResponse(nuevoUsuario.getUsername(), nuevoUsuario.getEmail(), nuevoUsuario.getListRols().toString(), "Usuario creado con exito");
    }

    @Override
    public AuthenticateResponse loginUsuario(AuthLoginRequest authLoginRequest) throws Exception {
        Authentication authentication = this.authenticate(authLoginRequest.email(), authLoginRequest.password());
        if (authentication==null){
            throw new BadCredentialsException("Fallo autenticacion en login.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtilService.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new AuthenticateResponse(userDetails.getUsername(),authentication.getAuthorities().toString() ,jwt);
    }

    @Override
    public Authentication authenticate(String email, String password){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        if (userDetails == null){
            throw new BadCredentialsException("No se pueden validar los datos de usuario.");
        }
        if (this.passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Datos de usuario no validos.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }


    private boolean emailExists(String email){
        return usuarioRepository.findByEmail(email).isPresent();
    }

}