package com.tp_anual_dds.domain;

public class Ubicacion {
    Double longitud;
    Double latitud;
    String direccion;
    String ciudad;
    String pais;

    public Ubicacion(Double vLongitud, Double vLatitud, String vDireccion, String vCiudad, String vPais) {
        longitud = vLongitud;
        latitud = vLatitud;
        direccion = vDireccion;
        ciudad = vCiudad;
        pais = vPais;
    }
}
