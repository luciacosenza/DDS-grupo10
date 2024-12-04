package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoServiceSelector;
import lombok.extern.java.Log;

@Log
public abstract class Notificador {

    private final MedioDeContactoServiceSelector medioDeContactoServiceSelector;

    public Notificador(MedioDeContactoServiceSelector vMedioDeContactoServiceSelector) {
        medioDeContactoServiceSelector = vMedioDeContactoServiceSelector;
    }

    public void enviarNotificacion(MedioDeContacto medioDeContacto, String asunto, String cuerpo) {
        MedioDeContactoService medioDeContactoService = medioDeContactoServiceSelector.obtenerMedioDeContactoService(medioDeContacto.getClass());
        medioDeContactoService.contactar(medioDeContacto.getId(), asunto, cuerpo);
    }
}