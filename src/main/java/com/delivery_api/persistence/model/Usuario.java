package com.delivery_api.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long id;

    @Column(name = "nombre_usuario", nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "contraseña", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private List<Rol> listRols;

    @ElementCollection
    @CollectionTable(name = "direcciones_usuarios", joinColumns = @JoinColumn(name = "usuario_id"))
    private List<Direccion> direcciones;

    @Column(length = 24)
    private String telefono;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Orden> ordenes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurantes_favoritos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Restaurant> favoritos;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    public Usuario(String username, String email, String password, List<Rol> listRols,
                   boolean isEnabled, boolean accountNoExpired,
                   boolean accountNoLocked, boolean credentialNoExpired) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.listRols = listRols;
        this.isEnabled = isEnabled;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
    }
}
