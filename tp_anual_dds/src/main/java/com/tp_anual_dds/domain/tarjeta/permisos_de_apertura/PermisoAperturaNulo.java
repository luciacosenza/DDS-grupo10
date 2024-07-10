package com.tp_anual_dds.domain.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.heladera.Heladera;

public class PermisoAperturaNulo extends PermisoApertura {
    public PermisoAperturaNulo() {
        heladerasPermitidas = new ArrayList<>();
        fechaOtorgamiento = null;
    }

    @Override
    public ArrayList<Heladera> getHeladerasPermitidas() {
        return heladerasPermitidas;
    }

    @Override
    public LocalDateTime getFechaOtorgamiento() {
        throw new UnsupportedOperationException("Permiso Nulo no tiene una fecha de otorgamiento.");
    }

    @Override
    public void actualizarFechaOtorgamiento(){}

    @Override
    public Boolean esHeladeraPermitida(Heladera heladera) {
        return heladerasPermitidas.contains(heladera);
    }

    @Override
    public void agregarHeladeraPermitida(Heladera heladera) {}

    @Override
    public void resetHeladerasPermitidas() {
        heladerasPermitidas.clear();
    }
    
    @Override
    public Integer cantidadHeladerasPermitidas() {
        return heladerasPermitidas.size();
    }
}
