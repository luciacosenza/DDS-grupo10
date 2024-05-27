package com.tp_anual_dds.domain;

public interface SensorSubject {
    public void agregar(Heladera observer);
    
    public void eliminar(Heladera observer);

    public void notificar();
}
