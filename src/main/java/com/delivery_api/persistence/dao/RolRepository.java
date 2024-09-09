package com.delivery_api.persistence.dao;

import com.delivery_api.persistence.model.Rol;
import com.delivery_api.utils.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findRoleByRol (RoleEnum rol);

    default Optional<Rol> findRoleByString(String rol){
        try {
            RoleEnum roleEnum = RoleEnum.valueOf(rol.toUpperCase());
            return findRoleByRol(roleEnum);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    };
}
