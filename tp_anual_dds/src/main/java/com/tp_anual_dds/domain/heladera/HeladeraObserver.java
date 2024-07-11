package com.tp_anual_dds.domain.heladera;

public interface HeladeraObserver {
    public void setTempActual(Float temperatura);

    public void alertarFraude();
}