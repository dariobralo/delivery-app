package com.delivery_api.utils.security;

import com.delivery_api.persistence.dao.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /*
     *
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optUsuario = this.usuarioRepository.findByEmail(email);

        if (optUsuario.isPresent()){
            List<? extends GrantedAuthority> authorities= optUsuario.get().getListRols()
                    .stream().map(SecurityAuthority::new).toList();

            return new User(optUsuario.get().getEmail(), optUsuario.get().getPassword(), authorities);
        }

        throw new UsernameNotFoundException("Usuario no encontrado -> " + email);
    }



}
