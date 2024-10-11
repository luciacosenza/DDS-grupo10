package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class NotificadorDeEstado extends Notificador {

    private final UbicadorHeladera ubicadorHeladera;
    private final HeladeraService heladeraService;

    @Getter
    @Setter
    private Integer cantidadHeladeras;
    
    public NotificadorDeEstado(MedioDeContactoService vMedioDeContactoService, UbicadorHeladera vUbicadorHeladera, HeladeraService vHeladeraService) {
        super(vMedioDeContactoService);
        ubicadorHeladera = vUbicadorHeladera;
        cantidadHeladeras = 10; // Esta cantidad es arbitraria, pero puede ser modificada
        heladeraService = vHeladeraService;
    }

    public void notificarEstado(Long heladeraId, Long medioDeContactoId, CondicionSuscripcion condicion) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        HeladeraActiva heladera = heladeraService.obtenerHeladera(heladeraId);

        ArrayList<HeladeraActiva> heladerasCercanas = ubicadorHeladera.obtenerHeladerasCercanasA(heladeraId, cantidadHeladeras);
        
        switch(condicion) {

            case VIANDAS_MIN -> {
                // Obtengo la Heladera más llena
                HeladeraActiva heladeraMasLlena = ubicadorHeladera.obtenerHeladeraMasLlena(heladerasCercanas);

                enviarNotificacion(
                medioDeContactoId,
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_title",
                heladera.getNombre()),
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body",
                heladera.getNombre(), heladera.viandasActuales(), heladeraMasLlena.getNombre(), heladeraMasLlena.getUbicacion().getDireccion()));
            }

            case VIANDAS_MAX -> {
                // Obtengo la Heladera menos llena
                HeladeraActiva heladeraMenosLlena = ubicadorHeladera.obtenerHeladeraMenosLlena(heladerasCercanas);

                enviarNotificacion(
                medioDeContactoId,
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmx_title",
                heladera.getNombre()),
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmx_body",
                (heladera.getCapacidad() - heladera.viandasActuales()), heladera.getNombre(), heladeraMenosLlena.getNombre(), heladeraMenosLlena.getUbicacion().getDireccion()));
            }

            case DESPERFECTO -> {
                enviarNotificacion(
                medioDeContactoId,
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_d_title",
                heladera.getNombre()),
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_d_body",
                obtenerNombresYDireccionesDe(heladerasCercanas)));
            }

            default -> {}
        }
    }

    public String obtenerNombresYDireccionesDe(ArrayList<HeladeraActiva> heladeras) {
        HeladeraActiva heladera1 = heladeras.removeFirst();
        String nombresYDirecciones = I18n.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_1", heladera1.getNombre(), heladera1.getUbicacion().getDireccion());
        
        for (HeladeraActiva heladera : heladeras) {
            nombresYDirecciones += I18n.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_i", heladera.getNombre(), heladera.getUbicacion().getDireccion());
        }

        return nombresYDirecciones;
    }
}