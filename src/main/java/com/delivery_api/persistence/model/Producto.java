package com.delivery_api.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private long id;

    private String titulo;

    private String descripcion;

    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private Restaurant restaurante;

    @ElementCollection
    @CollectionTable(name = "imagenes_producto", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "imagen",length = 1025)
    private List<String> ListaImagenes;

    private BigDecimal precio;

    private boolean estaDisponible = false;

    private boolean esVegetariano = false;

    private boolean aptoCeliaco = false;


}
