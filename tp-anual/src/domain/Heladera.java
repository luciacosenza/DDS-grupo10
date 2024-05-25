package domain;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Heladera {
    private String nombre;
    private String direccion;
    private Double longitud;
    private Double latitud;
    private ArrayList<Vianda> viandas;
    private Integer capacidad;
    private LocalDateTime fechaApertura;
    private Float tempMin;
    private Float tempMax;
    private Boolean activa;


    public Heladera(String vNombre, String vDireccion, Double vLongitud, Double vLatitud, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
        nombre = vNombre;
        direccion = vDireccion;
        longitud = vLongitud;
        latitud = vLatitud;
        viandas = vViandas;
        capacidad = vCapacidad;
        fechaApertura = vFechaApertura;
        tempMin = vTempMin;
        tempMax =vTempMax;
        activa = true;
    }

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public ArrayList<Vianda> getViandas() {
        return viandas;
    }

    public Boolean estaActiva() {
        return activa;
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
