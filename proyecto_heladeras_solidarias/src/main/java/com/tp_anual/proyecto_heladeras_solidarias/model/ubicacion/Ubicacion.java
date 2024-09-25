package com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Log
@Getter
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private final Double latitud;

    @NotNull
    private final Double longitud;

    @NotBlank
    private final String direccion;

    @NotBlank
    // TODO: A chequear el Pattern
    private final String codigoPostal;

    @NotBlank
    private final String ciudad;
    
    @NotBlank
    private final String pais;

    public Ubicacion(Double vLatitud, Double vLongitud, String vDireccion, String vCodigoPostal, String vCiudad, String vPais) {
        latitud = vLatitud;
        longitud = vLongitud;
        direccion = vDireccion;
        codigoPostal = vCodigoPostal;
        ciudad = vCiudad;
        pais = vPais;
    }

    public String getDireccionCompleta() {
        return direccion + ", " + ciudad + ", " + pais;
    }
}
