package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log
public class NotificadorDeEstado extends Notificador {

    private final UbicadorHeladera ubicadorHeladera;

    @Getter
    @Setter
    private Integer cantidadHeladeras;
    
    public NotificadorDeEstado(@Qualifier("medioDeContactoService") MedioDeContactoService vMedioDeContactoService, UbicadorHeladera vUbicadorHeladera) {
        super(vMedioDeContactoService);
        ubicadorHeladera = vUbicadorHeladera;
        cantidadHeladeras = 10; // Esta cantidad es arbitraria, pero puede ser modificada
    }

    public void notificarEstado(Heladera heladera, Long medioDeContactoId, CondicionSuscripcion condicion) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        ArrayList<Heladera> heladerasCercanas = ubicadorHeladera.obtenerHeladerasCercanasA(heladera, cantidadHeladeras);
        
        switch(condicion) {

            case VIANDAS_MIN -> {
                // Obtengo la Heladera más llena
                Heladera heladeraMasLlena = ubicadorHeladera.obtenerHeladeraMasLlena(heladerasCercanas);

                enviarNotificacion(
                medioDeContactoId,
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_title",
                heladera.getNombre()),
                I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body",
                heladera.getNombre(), heladera.viandasActuales(), heladeraMasLlena.getNombre(), heladeraMasLlena.getUbicacion().getDireccion()));
            }

            case VIANDAS_MAX -> {
                // Obtengo la Heladera menos llena
                Heladera heladeraMenosLlena = ubicadorHeladera.obtenerHeladeraMenosLlena(heladerasCercanas);

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

    public String obtenerNombresYDireccionesDe(ArrayList<Heladera> heladeras) {
        Heladera heladera1 = heladeras.removeFirst();
        String nombresYDirecciones = I18n.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_1", heladera1.getNombre(), heladera1.getUbicacion().getDireccion());
        
        for (Heladera heladera : heladeras) {
            nombresYDirecciones += I18n.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_i", heladera.getNombre(), heladera.getUbicacion().getDireccion());
        }

        return nombresYDirecciones;
    }
}