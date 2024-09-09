package com.delivery_api.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"nombre", "descripcion", "precio", "publicado_por",
        "disponible", "vegetariano", "apto_celiaco", "imagenes"})
public record ProductResponse(
        String nombre,
        String descripcion,
        String publicado_por,
        Double precio,
        List<String> imagenes,
        Boolean disponible,
        Boolean vegetariano,
        Boolean apto_celiaco
) {
}
