package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.EMail;

public class EnvioEMail extends EnvioDeDatos {

    @Override
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo) {
        EMail eMail = colaborador.getMedioDeContacto(EMail.class);
        eMail.contactar(asunto, cuerpo.formatted(colaborador.getPersona().getNombre(), eMail));

        confirmarSending();
    }
}
