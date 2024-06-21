package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.ColaboradorHumano;
import com.tp_anual_dds.domain.EMail;

public class EnvioEMail extends EnvioDeDatos {
    @Override
    public void send(ColaboradorHumano colaborador, String asunto, String cuerpo) {
        EMail eMail = colaborador.getContacto(EMail.class);
        
        eMail.contactar(asunto.formatted(colaborador.getNombre(), eMail), cuerpo);
    }
}
