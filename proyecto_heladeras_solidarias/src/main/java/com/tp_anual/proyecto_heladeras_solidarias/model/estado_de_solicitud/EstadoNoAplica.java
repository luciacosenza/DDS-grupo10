package com.tp_anual.proyecto_heladeras_solidarias.model.estado_de_solicitud;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import lombok.extern.java.Log;

@Log
public class EstadoNoAplica implements EstadoSolicitud {

    @Override
    public void manejar(TarjetaColaborador tarjeta) {}
}
