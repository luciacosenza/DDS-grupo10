package com.tp_anual.proyecto_heladeras_solidarias.domain.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaColaborador;
import lombok.extern.java.Log;

@Log
public class EstadoNoAplica implements EstadoSolicitud {

    @Override
    public void manejar(TarjetaColaborador tarjeta) {}
}
