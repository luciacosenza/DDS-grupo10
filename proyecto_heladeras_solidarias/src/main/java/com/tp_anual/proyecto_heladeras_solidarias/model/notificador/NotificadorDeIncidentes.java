package com.tp_anual.proyecto_heladeras_solidarias.model.notificador;


import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicador.UbicadorTecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;

@Getter
public class NotificadorDeIncidentes extends Notificador {
    private final UbicadorTecnico ubicador;
    
    public NotificadorDeIncidentes() {
        ubicador = new UbicadorTecnico();
    }

    public void notificarIncidente(Incidente incidente) {
        Tecnico tecnicoAAlertar = ubicador.obtenerTecnicoCercanoA(incidente);
        tecnicoAAlertar.agregarAPendientes(incidente);
        
        HeladeraActiva heladera = incidente.getHeladera();
        MedioDeContacto contacto = tecnicoAAlertar.getMedioDeContacto();
        enviarNotificacion(contacto, I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", heladera.getNombre()), I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", incidente.getClass().getSimpleName(), heladera.getNombre()));
    }
}