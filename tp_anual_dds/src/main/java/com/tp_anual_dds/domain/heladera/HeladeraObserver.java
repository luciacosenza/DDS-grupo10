package com.tp_anual_dds.domain.heladera;

import com.tp_anual_dds.domain.incidente.Alerta;

public interface HeladeraObserver {
    public void setTempActual(Float temperatura);

    public void producirAlerta(Alerta.TipoAlerta tipo);
}