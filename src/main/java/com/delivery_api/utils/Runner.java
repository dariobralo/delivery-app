package com.delivery_api.utils;

import com.delivery_api.persistence.dao.RolRepository;
import com.delivery_api.persistence.dao.UsuarioRepository;
import com.delivery_api.persistence.model.Rol;
import com.delivery_api.persistence.model.Usuario;
import com.delivery_api.utils.enums.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

public class Runner implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public Runner(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.rolRepository.count() == 0) {
            this.rolRepository.saveAll(List.of(
                    new Rol(RoleEnum.ROLE_CUSTOMER),
                    new Rol(RoleEnum.ROLE_RESTAURANT_OWNER),
                    new Rol(RoleEnum.ROLE_ADMIN),
                    new Rol(RoleEnum.ROLE_DEVELOPER)
            ));
        }

        if (this.usuarioRepository.count() == 0){
            var encoders = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.usuarioRepository.saveAll(List.of(
                    new Usuario("Usuario-Customer", "customer@gmail.com",
                            encoders.encode("1234"),
                            List.of(this.rolRepository.findRoleByString("ROLE_CUSTOMER").get()),
                            true, true,
                            true, true),
                    new Usuario("Usuario-Owner", "owner@gmail.com",
                            "1234",
                            List.of(this.rolRepository.findRoleByRol(RoleEnum.ROLE_RESTAURANT_OWNER).get()),
                            true, true,
                            true, true),
                    new Usuario("Usuario-Admin", "admin@gmail.com",
                            "1234",
                            List.of(this.rolRepository.findRoleByRol(RoleEnum.ROLE_ADMIN).get()),
                            true, true,
                            true, true),
                    new Usuario("Usuario-Developer", "dev@gmail.com",
                            "1234",
                            List.of(this.rolRepository.findRoleByRol(RoleEnum.ROLE_DEVELOPER).get()),
                            true, true,
                            true, true)
            ));
        }
    }
}
