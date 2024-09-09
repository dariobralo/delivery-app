package com.delivery_api.persistence.model;

import com.delivery_api.utils.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, unique = true)
    private RoleEnum rol;

    public Rol(RoleEnum rol) {
        this.rol = rol;
    }
}
