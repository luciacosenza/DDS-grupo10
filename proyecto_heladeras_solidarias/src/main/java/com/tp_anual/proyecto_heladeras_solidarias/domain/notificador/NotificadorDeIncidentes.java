package com.tp_anual.proyecto_heladeras_solidarias.domain.notificador;


import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicador.UbicadorTecnico;

public class NotificadorDeIncidentes {
    private final UbicadorTecnico ubicador = new UbicadorTecnico();
    
    public NotificadorDeIncidentes() {}

    public void notificarIncidente(Incidente incidente) {
        Tecnico tecnicoAAlertar = ubicador.obtenerTecnicoCercanoA(incidente);
        tecnicoAAlertar.agregarAPendientes(incidente);
        
        HeladeraActiva heladera = incidente.getHeladera();
        tecnicoAAlertar.getMedioDeContacto().contactar("Ocurrió un Incidente en la Heladera " + heladera.getNombre(), ". Necesitamos que atiendas lo antes posible el incidente de tipo " + incidente.getClass().getName() + " en la Heladera " + heladera.getNombre() + ".");
    }
}