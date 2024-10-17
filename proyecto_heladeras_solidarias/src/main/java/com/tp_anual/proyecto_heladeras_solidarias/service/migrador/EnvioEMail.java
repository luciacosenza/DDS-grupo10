package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.EMailService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class EnvioEMail extends EnvioDeDatos {

    private final EMailService eMailService;

    public EnvioEMail(EMailService vEMailService) {
        eMailService = vEMailService;
    }

    @Override
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo) {
        EMail eMail = colaborador.getMedioDeContacto(EMail.class);
        eMailService.contactar(eMail.getId(), asunto, cuerpo.formatted(colaborador.getNombre(), eMail));

        confirmarSending();
    }
}
