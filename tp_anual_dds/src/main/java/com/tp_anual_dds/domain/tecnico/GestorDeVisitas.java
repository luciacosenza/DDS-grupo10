package com.tp_anual_dds.domain.tecnico;

import java.util.ArrayList;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.sistema.Sistema;

public class GestorDeVisitas {
    private final ArrayList<Visita> visitas = new ArrayList<>();

    public GestorDeVisitas() {}

    public void gestionarVisita() {
        // Obtengo la primer Visita registrada
        Visita visita = visitas.removeFirst();
        
        // Chequeo si fue exitosa. Si no lo fue, vuelvo a llamar a un Tecnico para que vaya a ocuparse
        if(!visita.getEstado()) {
            Incidente incidente = visita.getIncidente();
            Sistema.getNotificadorDeIncidentes().notificarIncidente(incidente);
        }

        // Tanto si fue exitosa o no, se da de alta en el Sistema
        visita.darDeAlta();
    }

    public void agregarVisita(Visita visita) {
        visitas.add(visita);
        gestionarVisita();
    }
}