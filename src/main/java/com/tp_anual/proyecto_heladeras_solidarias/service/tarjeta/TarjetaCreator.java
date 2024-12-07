package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta.DatosInvalidosCrearTarjetaColaboradorException;
import com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta.DatosInvalidosCrearTarjetaPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;

public interface TarjetaCreator {
    public Tarjeta crearTarjeta(Object titular) throws DatosInvalidosCrearTarjetaColaboradorException, DatosInvalidosCrearTarjetaPESVException;
}
