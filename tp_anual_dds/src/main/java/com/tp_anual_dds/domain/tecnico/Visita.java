package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual_dds.sistema.Sistema;

public class Visita {
    private Tecnico tecnico;
    private LocalDateTime fecha;
    private String descripcion;
    private String foto;
    private Boolean estadoConsulta;

    public Visita(Tecnico vTecnico, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vEstadoConsulta) {
        tecnico = vTecnico;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estadoConsulta = vEstadoConsulta;
    }

    public void registrar() {
        Sistema.agregarVisita(this);
    }
}
