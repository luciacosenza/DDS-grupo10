package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

public abstract class TarjetaService {

    public TarjetaService() {}

    public abstract Boolean puedeUsar(String tarjetaId);
}
