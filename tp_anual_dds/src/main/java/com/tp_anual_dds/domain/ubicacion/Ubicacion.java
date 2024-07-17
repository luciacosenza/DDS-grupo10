package com.tp_anual_dds.domain.ubicacion;

public class Ubicacion {
    Double latitud;
    Double longitud;
    String direccion;
    String ciudad;
    String pais;

    public Ubicacion(Double vLatitud, Double vLongitud, String vDireccion, String vCiudad, String vPais) {
        latitud = vLatitud;
        longitud = vLongitud;
        direccion = vDireccion;
        ciudad = vCiudad;
        pais = vPais;
    }
}
