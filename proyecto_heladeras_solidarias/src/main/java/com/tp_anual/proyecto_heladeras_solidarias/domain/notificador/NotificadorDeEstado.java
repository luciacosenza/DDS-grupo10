package com.tp_anual.proyecto_heladeras_solidarias.domain.notificador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicador.UbicadorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.message_loader.I18n;

public class NotificadorDeEstado {
    private final UbicadorHeladera ubicador = new UbicadorHeladera();
    private Integer cantidadHeladeras = 10; // Esta cantidad es arbitraria, pero puede ser modificada
    
    public NotificadorDeEstado() {}

    public Integer getCantidadHeladeras() {
        return cantidadHeladeras;
    }

    public void setCantidadHeladeras(Integer vCantidadHeladeras) {
        cantidadHeladeras = vCantidadHeladeras;
    }

    public void enviarNotificacion(MedioDeContacto contacto, String asunto, String cuerpo) {
        contacto.contactar(asunto, cuerpo);
    }

    public void notificarEstado(HeladeraActiva heladera, CondicionSuscripcion condicion, MedioDeContacto contacto) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        ArrayList<HeladeraActiva> heladerasCercanas = ubicador.obtenerHeladerasCercanasA(heladera, cantidadHeladeras);
        
        switch(condicion) {
        
        case VIANDAS_MIN -> {
            // Obtengo la Heladera más llena
            HeladeraActiva heladeraMasLlena = ubicador.obtenerHeladeraMasLlena(heladerasCercanas);

            enviarNotificacion(
            contacto,
            I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_title",
            heladera.getNombre()),
            I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body",
            heladera.getNombre(), heladera.viandasActuales(), heladeraMasLlena.getNombre(), heladeraMasLlena.getUbicacion().getDireccion()));
        }
        
        case VIANDAS_MAX -> {
            // Obtengo la Heladera menos llena
            HeladeraActiva heladeraMenosLlena = ubicador.obtenerHeladeraMenosLlena(heladerasCercanas);

            enviarNotificacion(
            contacto,
            I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmx_title",
            heladera.getNombre()),
            I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_vmn_body",
            (heladera.getCapacidad() - heladera.viandasActuales()), heladera.getNombre(), heladeraMenosLlena.getNombre(), heladeraMenosLlena.getUbicacion().getDireccion()));
        }

        case DESPERFECTO -> {

            enviarNotificacion(
            contacto,
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
        String nombresYDirecciones = I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_d_body", heladera1.getNombre(), heladera1.getUbicacion().getDireccion());
        
        for (HeladeraActiva heladera : heladeras) {
            nombresYDirecciones += I18n.getMessage("notificador.NotificadorDeEstado.notificarEstado_outer_message_d_body", heladera.getNombre(), heladera.getUbicacion().getDireccion());
        }

        return nombresYDirecciones;
    }
}
