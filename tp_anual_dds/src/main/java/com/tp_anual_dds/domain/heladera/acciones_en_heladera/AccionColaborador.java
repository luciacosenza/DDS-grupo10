package com.tp_anual_dds.domain.heladera.acciones_en_heladera;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public abstract class AccionColaborador extends AccionHeladera {
    protected ColaboradorHumano responsable;

    public ColaboradorHumano getResponsable() {
        return responsable;
    }
}