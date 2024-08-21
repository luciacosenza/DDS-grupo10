package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;

public interface EstadoSolicitud {
    public void manejar(TarjetaColaborador tarjeta);
}