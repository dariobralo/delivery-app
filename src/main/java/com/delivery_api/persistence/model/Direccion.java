package com.delivery_api.persistence.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Direccion {

    private String direccion;
    private String timbre;
    private String nota;
    private String ciudad;
    private String pais;

}
