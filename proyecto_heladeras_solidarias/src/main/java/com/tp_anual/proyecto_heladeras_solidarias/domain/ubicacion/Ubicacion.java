package com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion;

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

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getDireccionCompleta() {
        return direccion + ", " + ciudad + ", " + pais;
    }
}
