package  com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

public class GestorDeSuscripciones {
    private final ArrayList<Suscripcion> suscripciones;

    public GestorDeSuscripciones() {
        suscripciones = new ArrayList<>();
    }

    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }
    
    public void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }

    public ArrayList<Suscripcion> suscripcionesPorHeladera(Heladera heladera) {
        return suscripciones.stream().filter(suscripcion -> suscripcion.getHeladera().equals(heladera)).collect(Collectors.toCollection(ArrayList::new));
    }
}