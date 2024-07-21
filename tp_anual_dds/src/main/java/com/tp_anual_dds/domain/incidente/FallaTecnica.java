package com.tp_anual_dds.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class FallaTecnica extends Incidente {
    private Colaborador colaborador;
    private String descripcion;
    private String foto;

    public FallaTecnica(LocalDateTime vFecha, HeladeraActiva vHeladera, Colaborador vColaborador, String vDescripcion, String vFoto) {
        fecha = vFecha;
        heladera = vHeladera;
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
