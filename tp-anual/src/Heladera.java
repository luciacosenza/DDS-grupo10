package src;

import java.util.ArrayList;
import java.util.Date;

public class Heladera {
    private String nombre;
    private String direccion;
    private Integer longitud;
    private Integer latitud;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private Date fechaApertura;

    public Heladera(String vNombre, String vDireccion, Integer vLongitud, Integer vLatitud, ArrayList<Vianda> vViandas, Integer vCapacidad, Date vFechaApertura) {
        nombre = vNombre;
        direccion = vDireccion;
        longitud = vLongitud;
        latitud = vLatitud;
        viandas = vViandas;
        capacidad = vCapacidad;
        fechaApertura = vFechaApertura;
    }
}
