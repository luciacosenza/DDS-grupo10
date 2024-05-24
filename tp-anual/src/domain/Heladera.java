package domain;

import java.util.ArrayList;
import java.time.LocalDate;

public class Heladera {
    private String nombre;
    private String direccion;
    private Double longitud;
    private Double latitud;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private LocalDate fechaApertura;
    private Float tempMin;
    private Float tempMax;
    private Boolean activa;


    public Heladera(String vNombre, String vDireccion, Double vLongitud, Double vLatitud, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDate vFechaApertura) {
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

    public ArrayList<Vianda> viandas() {
        return viandas;
    }

    // actualizarEstado()
    // temperatura(Temperatura temperatura)
    // verificarTemperatura()

    public Vianda retirarVianda() {
        return viandas.remove(0);
    }

    public void agregarVianda(Vianda vianda) {
        viandas.add(vianda);
    }
    
    // alertaMovimiento()
    // alertaTemperatura()
}
