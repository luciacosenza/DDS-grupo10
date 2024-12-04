package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.Tarjeta;

import java.util.List;

public abstract class TarjetaService {

    public TarjetaService() {}

    public abstract Tarjeta obtenerTarjeta(String tarjetaId);

    public abstract Tarjeta guardarTarjeta(Tarjeta tarjeta);

    public abstract Tarjeta crearTarjeta(Long titularId);

    public abstract Boolean puedeUsar(String tarjetaId);
}
