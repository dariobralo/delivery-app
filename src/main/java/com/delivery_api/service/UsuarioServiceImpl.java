package com.delivery_api.service;

import com.delivery_api.persistence.dao.RolRepository;
import com.delivery_api.persistence.dao.UsuarioRepository;
import com.delivery_api.persistence.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     *
     */
    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> getUsuario(String verificationToken) {
        return Optional.empty();
    }

    @Override
    public void saveRegisteredUsuario(Usuario usuario) {

    }

    @Override
    public void deleteUsuario(Usuario usuario) {

    }

    /*
     * Metodos de la clase
     */
    private boolean emailExists(String email){
        return usuarioRepository.findByEmail(email).isPresent();
    }

}
