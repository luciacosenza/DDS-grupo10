package com.tp_anual.proyecto_heladeras_solidarias.domain.notificador;


import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicador.UbicadorTecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class NotificadorDeIncidentes {
    private final UbicadorTecnico ubicador = new UbicadorTecnico();
    
    public NotificadorDeIncidentes() {}

    public void notificarIncidente(Incidente incidente) {
        Tecnico tecnicoAAlertar = ubicador.obtenerTecnicoCercanoA(incidente);
        tecnicoAAlertar.agregarAPendientes(incidente);
        
        HeladeraActiva heladera = incidente.getHeladera();
        tecnicoAAlertar.getMedioDeContacto().contactar(I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", heladera.getNombre()), I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", incidente.getClass().getSimpleName(), heladera.getNombre()));
    }
}