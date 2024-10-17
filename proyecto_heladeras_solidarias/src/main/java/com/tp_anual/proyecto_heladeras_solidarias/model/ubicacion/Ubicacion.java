package com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;

@Entity
@Log
@Getter
public class Ubicacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Latitud y longitud serán nulas para todos los objetos menos para las heladeras (ya que para estas nos interesa saber su ubicación exacta)

    private Double latitud; // final

    private Double longitud;    // final

    private String direccion;   // final

    // TODO: A chequear el Pattern
    private String codigoPostal;    // final

    private String ciudad;  // final
    
    private String pais;    // final

    public Ubicacion() {}

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
