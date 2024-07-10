package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

public class Visita {
    private LocalDateTime fecha;
    private String descripcion;
    private String foto;
    private Boolean estadoConsulta;

    public Visita(LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vEstadoConsulta) {
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estadoConsulta = vEstadoConsulta;
    }
}
