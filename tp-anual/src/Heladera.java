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

    public Heladera(String nombr, String direc, Integer longi, Integer lati, ArrayList<Vianda> viand, Integer capac, Date fechaA) {
        nombre = nombr;
        direccion = direc;
        longitud = longi;
        latitud = lati;
        viandas = viand;
        capacidad = capac;
        fechaApertura = fechaA;
    }
}
