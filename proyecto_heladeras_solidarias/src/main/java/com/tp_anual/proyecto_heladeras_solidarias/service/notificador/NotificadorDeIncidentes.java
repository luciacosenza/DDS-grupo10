package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;


import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.IncidenteService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tecnico.TecnicoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorTecnico;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class NotificadorDeIncidentes extends Notificador {

    private final UbicadorTecnico ubicadorTecnico;
    private final IncidenteService incidenteService;
    private final TecnicoService tecnicoService;

    public NotificadorDeIncidentes(MedioDeContactoService vMedioDeContactoService, UbicadorTecnico vUbicadorTecnico, IncidenteService vIncidenteService, TecnicoService vTecnicoService) {
        super(vMedioDeContactoService);
        ubicadorTecnico = vUbicadorTecnico;
        incidenteService = vIncidenteService;
        tecnicoService = vTecnicoService;
    }

    public void notificarIncidente(Long incidenteId) {
        Incidente incidente = incidenteService.obtenerIncidente(incidenteId);

        Tecnico tecnicoAAlertar = ubicadorTecnico.obtenerTecnicoCercanoA(incidenteId);
        tecnicoAAlertar.agregarAPendientes(incidente);
        tecnicoService.guardarTecnico(tecnicoAAlertar);
        
        HeladeraActiva heladera = incidente.getHeladera();
        MedioDeContacto medioDeContacto = tecnicoAAlertar.getMedioDeContacto();
        enviarNotificacion(medioDeContacto.getId(), I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", heladera.getNombre()), I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body", incidente.getClass().getSimpleName(), heladera.getNombre()));
    }
}