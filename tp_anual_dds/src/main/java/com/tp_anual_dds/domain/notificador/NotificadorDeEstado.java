package com.tp_anual_dds.domain.notificador;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.suscripcion.Suscripcion.CondicionSuscripcion;

public class NotificadorDeEstado {
    public NotificadorDeEstado() {}

    public void enviarNotificacion(MedioDeContacto contacto, String asunto, String cuerpo) {
        contacto.contactar(asunto, cuerpo);
    }

    public void notificarEstado(HeladeraActiva heladera, CondicionSuscripcion condicion, MedioDeContacto contacto) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        switch(condicion) {
        
        case VIANDAS_MIN -> enviarNotificacion(
                contacto,
            "La heladera " + heladera.getNombre() + " se está vaciando.",
            "La heladera en cuestión tiene " + heladera.viandasActuales() + " viandas disponibles. " +
            "Sería conveniente traer viandas de la heldera %o");
            // TODO Completar %o con la Heladera más llena de las cercanas
        
        case VIANDAS_MAX -> enviarNotificacion(
            contacto, "La heladera " + heladera.getNombre() + " está casi llena.",
            "Faltan " + (heladera.getCapacidad() - heladera.viandasActuales()) + " viandas para llenar la heladera en cuestión. " +
            "Sería conveniente llevar viandas a la heladera %o");
            // TODO Completar %o con la Heladera menos llena de las cercanas

        case DESPERFECTO -> enviarNotificacion(
            contacto, "La heladera "+ heladera.getNombre() + " ha sufrido un desperfecto.",
            "Las viandas deben ser trasladadas a %s.");
            // TODO Completar %s con la Heladera/s más cercanas

        default -> {}

        }
    }
}
