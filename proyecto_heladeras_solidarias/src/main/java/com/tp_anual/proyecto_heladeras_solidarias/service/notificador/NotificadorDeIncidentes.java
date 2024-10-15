package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoServiceSelector;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorTecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class NotificadorDeIncidentes extends Notificador {

    private final UbicadorTecnico ubicadorTecnico;
    private final TecnicoService tecnicoService;

    public NotificadorDeIncidentes(MedioDeContactoServiceSelector vMedioDeContactoServiceSelector, UbicadorTecnico vUbicadorTecnico, TecnicoService vTecnicoService) {
        super(vMedioDeContactoServiceSelector);
        ubicadorTecnico = vUbicadorTecnico;
        tecnicoService = vTecnicoService;
    }

    public void notificarIncidente(Incidente incidente) {
        Tecnico tecnicoAAlertar = ubicadorTecnico.obtenerTecnicoCercanoA(incidente);
        tecnicoService.agregarAPendientes(tecnicoAAlertar.getId(), incidente);
        tecnicoService.guardarTecnico(tecnicoAAlertar);
        
        Heladera heladera = incidente.getHeladera();
        MedioDeContacto medioDeContacto = tecnicoAAlertar.getMedioDeContacto();
        enviarNotificacion(medioDeContacto, I18n.getMessage("notificador.NotificadorDeIncidentes.notificarEstado_outer_message_title", heladera.getNombre()), I18n.getMessage("notificador.NotificadorDeIncidentes.notificarEstado_outer_message_body", incidente.getClass().getSimpleName(), heladera.getNombre()));
    }
}