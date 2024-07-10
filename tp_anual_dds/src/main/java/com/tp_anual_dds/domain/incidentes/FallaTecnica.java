package com.tp_anual_dds.domain.incidentes;

import com.tp_anual_dds.domain.colaborador.Colaborador;

public class FallaTecnica {
    private Colaborador colaborador;
    private String descripcion;
    private String foto;

    public FallaTecnica(Colaborador vColaborador, String vDescripcion, String vFoto) {
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
