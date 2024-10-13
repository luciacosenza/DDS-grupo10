package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public abstract class Notificador {

    private final MedioDeContactoService medioDeContactoService;

    public Notificador(MedioDeContactoService vMedioDeContactoService) {
        medioDeContactoService = vMedioDeContactoService;
    }

    public void enviarNotificacion(Long medioDeContactoId, String asunto, String cuerpo) {
        medioDeContactoService.contactar(medioDeContactoId, asunto, cuerpo);
    }
}