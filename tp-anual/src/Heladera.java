package src;

import java.util.ArrayList;
import java.time.LocalDate;

public class Heladera {
    private String nombre;
    private String direccion;
    private Integer longitud;
    private Integer latitud;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private LocalDate fechaApertura;
    private Float tempMin;
    private Float tempMax;
    private Boolean activa;


    public Heladera(String vNombre, String vDireccion, Integer vLongitud, Integer vLatitud, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDate vFechaApertura) {
        nombre = vNombre;
        direccion = vDireccion;
        longitud = vLongitud;
        latitud = vLatitud;
        viandas = vViandas;
        capacidad = vCapacidad;
        fechaApertura = vFechaApertura;
    }

    // darDeAlta()
    // darDeBaja()
    // modificar()
    // actualizarEstado()
    // avisarAColaborador()
}
