package  com.tp_anual_dds.domain.suscripcion;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.tp_anual_dds.domain.heladera.Heladera;

public class GestorSuscripciones {
    private final ArrayList<Suscripcion> suscripciones;

    public GestorSuscripciones() {
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