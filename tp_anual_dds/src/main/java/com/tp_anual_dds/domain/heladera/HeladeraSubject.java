package com.tp_anual_dds.domain.heladera;

import com.tp_anual_dds.domain.suscripcion.Suscripcion;

public interface HeladeraSubject {
    public void notificarColaborador(Suscripcion suscripcion, String asunto, String cuerpo);
}
