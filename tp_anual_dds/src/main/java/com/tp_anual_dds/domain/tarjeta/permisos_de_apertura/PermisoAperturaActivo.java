package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.heladera.Heladera;

public class PermisoAperturaActivo extends PermisoApertura {
    public PermisoAperturaActivo() {
        heladerasPermitidas = new ArrayList<>();
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public ArrayList<Heladera> getHeladerasPermitidas() {
        return heladerasPermitidas;
    }

    @Override
    public LocalDateTime getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }

    @Override
    public void actualizarFechaOtorgamiento(){
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public Boolean esHeladeraPermitida(Heladera heladera) {
        return heladerasPermitidas.contains(heladera);
    }

    @Override
    public void agregarHeladeraPermitida(Heladera heladera) {
        heladerasPermitidas.add(heladera);
    }

    @Override
    public void resetHeladerasPermitidas() {
        heladerasPermitidas.clear();
    }
    
    @Override
    public Integer cantidadHeladerasPermitidas() {
        return heladerasPermitidas.size();
    }
}
