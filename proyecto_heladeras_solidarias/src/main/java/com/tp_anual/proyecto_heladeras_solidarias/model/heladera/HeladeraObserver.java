package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;

public interface HeladeraObserver {
    public void setTempActual(Float temperatura);

    public void producirAlerta(Alerta.TipoAlerta tipo);
}