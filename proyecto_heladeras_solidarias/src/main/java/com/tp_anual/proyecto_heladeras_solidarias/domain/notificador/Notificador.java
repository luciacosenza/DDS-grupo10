package com.tp_anual.proyecto_heladeras_solidarias.domain.notificador;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;

public abstract class Notificador {
    public void enviarNotificacion(MedioDeContacto contacto, String asunto, String cuerpo) {
        contacto.contactar(asunto, cuerpo);
    }
}