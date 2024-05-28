package com.tp_anual_dds.domain;

public interface HeladeraObserver { // Faltaria chequear si es correcto implementar una sola interfaz de Observer para todos los sensores o si habria que hacer una por sensor
    public void setTempActual(Float temperatura);

    public void alertarMovimiento();
}