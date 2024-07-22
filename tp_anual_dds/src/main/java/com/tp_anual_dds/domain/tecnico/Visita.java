package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual_dds.sistema.Sistema;

public class Visita {
    private final Tecnico tecnico;
    private final LocalDateTime fecha;
    private final String descripcion;
    private final String foto;
    private final Boolean exitosa;

    public Visita(Tecnico vTecnico, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vExitosa) {
        tecnico = vTecnico;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        exitosa = vExitosa;
    }

    public Tecnico getTecnico() {
        return tecnico;
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

    public Boolean getExitosa() {
        return exitosa;
    }

    public void darDeAlta() {
        Sistema.agregarVisita(this);
    }

    public void darDeBaja() {
        Sistema.eliminarVisita(this);
    }
}
