package com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico;

import java.util.ArrayList;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.Getter;

@Getter
public class GestorDeVisitas {
    private final ArrayList<Visita> visitas;

    public GestorDeVisitas() {
        visitas = new ArrayList<>();
    }

    public void gestionarVisita() {
        // Obtengo la primer Visita registrada
        Visita visita = visitas.removeFirst();
        
        // Chequeo si fue exitosa. Si no lo fue, vuelvo a llamar a un Tecnico para que vaya a ocuparse
        if (!visita.getEstado()) {
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