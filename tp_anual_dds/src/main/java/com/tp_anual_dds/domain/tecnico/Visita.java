package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.sistema.Sistema;

public class Visita {
    private final Tecnico tecnico;
    private final Incidente incidente;
    private final LocalDateTime fecha;
    private final String descripcion;
    private final String foto;
    private final Boolean estado;

    public Visita(Tecnico vTecnico, Incidente vIncidente, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vExitosa) {
        tecnico = vTecnico;
        incidente = vIncidente;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estado = vExitosa;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void darDeAlta() {
        Sistema.agregarVisita(this);
    }

    public void darDeBaja() {
        Sistema.eliminarVisita(this);
    }
}
