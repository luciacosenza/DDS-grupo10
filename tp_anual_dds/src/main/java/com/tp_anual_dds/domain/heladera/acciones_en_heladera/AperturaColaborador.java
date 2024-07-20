package com.tp_anual_dds.domain.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class AperturaColaborador extends AccionColaborador {
    public AperturaColaborador(LocalDateTime vFecha, HeladeraActiva vHeladera, ColaboradorHumano vResponsable) {
        fecha = vFecha;
        heladera = vHeladera;
        responsable = vResponsable;
    }
}
