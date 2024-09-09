package com.delivery_api.persistence.model;

import com.delivery_api.utils.enums.ESTADO_ORDEN;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cliente_id")
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private Restaurant restaurant;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_de_orden", length = 24)
    private ESTADO_ORDEN estadoOrden = ESTADO_ORDEN.PENDIENTE;

    @OneToMany(mappedBy = "orden", fetch = FetchType.EAGER)
    private List<DetalleOrden> listaDetalles = new ArrayList<>();

    private BigDecimal montoTotal = null;

}
