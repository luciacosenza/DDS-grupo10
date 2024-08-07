package com.tp_anual_dds.domain.ubicacion;

public class Ubicacion {
    private final Double latitud;
    private final Double longitud;
    private final String direccion;
    private final String ciudad;
    private final String pais;

    public Ubicacion(Double vLatitud, Double vLongitud, String vDireccion, String vCiudad, String vPais) {
        latitud = vLatitud;
        longitud = vLongitud;
        direccion = vDireccion;
        ciudad = vCiudad;
        pais = vPais;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }
}
