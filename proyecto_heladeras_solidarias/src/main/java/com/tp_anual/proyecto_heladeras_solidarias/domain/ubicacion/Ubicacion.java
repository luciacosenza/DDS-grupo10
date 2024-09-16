package com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion;

import lombok.Getter;

@Getter
public class Ubicacion {
    private final Double latitud;
    private final Double longitud;
    private final String direccion;
    private final String codigoPostal;
    private final String ciudad;
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
