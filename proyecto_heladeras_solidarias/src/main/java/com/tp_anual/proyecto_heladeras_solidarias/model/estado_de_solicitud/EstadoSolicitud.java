package com.tp_anual.proyecto_heladeras_solidarias.model.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;

public interface EstadoSolicitud {
    public void manejar(TarjetaColaborador tarjeta);
}