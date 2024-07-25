package com.tp_anual_dds.broker;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual_dds.sistema.Sistema;

public class MensajeEstado implements Mensaje {
    private final HeladeraActiva heladera;
    private final CondicionSuscripcion condicion;
    private final MedioDeContacto contacto;

    public MensajeEstado(HeladeraActiva vHeladera, CondicionSuscripcion vCondicion, MedioDeContacto vContacto) {
        heladera = vHeladera;
        condicion = vCondicion;
        contacto = vContacto;
    }

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    public CondicionSuscripcion getCondicion() {
        return condicion;
    }

    public MedioDeContacto getContacto() {
        return contacto;
    }

    @Override
    public void procesar() {
        Sistema.getNotificadorDeEstado().notificarEstado(heladera, condicion, contacto);
    }
}
