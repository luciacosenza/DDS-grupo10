package  com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;

public class GestorDeSuscripciones {
    private final ArrayList<Suscripcion> suscripciones = new ArrayList<>();

    public GestorDeSuscripciones() {}

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