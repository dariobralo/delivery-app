package com.delivery_api.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "restaurantes")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurante")
    private long id;

    private String nombre;

    private String presentacion;

    private LocalDateTime fechaRegistro;

    private String horariosAtencion;

    private boolean abierto;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private Usuario administrador;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Orden> ordenes = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "telefonos_restaurante", joinColumns = @JoinColumn(name = "restaurante_id"))
    @Column(length = 25)
    private List<String> telefono;

    @Embedded
    private Direccion direccion;

    @ElementCollection
    @CollectionTable(name = "redes_restaurante", joinColumns = @JoinColumn(name = "restaurante_id"))
    @Column(name = "redes_contacto",length = 1025)
    private List<String> redesSociales;

    @ElementCollection
    @CollectionTable(name = "imagenes_restaurante", joinColumns = @JoinColumn(name = "restaurante_id"))
    @Column(name = "imagen",length = 1025)
    private List<String> imagenes;


}
