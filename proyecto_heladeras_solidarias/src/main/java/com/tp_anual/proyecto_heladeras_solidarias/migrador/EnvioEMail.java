package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.EMail;

public class EnvioEMail extends EnvioDeDatos {
    @Override
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo) {
        EMail eMail = colaborador.getContacto(EMail.class);
        
        eMail.contactar(asunto, cuerpo.formatted(colaborador.getPersona().getNombre(), eMail));
    }
}
