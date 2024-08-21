package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Alerta;

public interface HeladeraObserver {
    public void setTempActual(Float temperatura);

    public void producirAlerta(Alerta.TipoAlerta tipo);
}