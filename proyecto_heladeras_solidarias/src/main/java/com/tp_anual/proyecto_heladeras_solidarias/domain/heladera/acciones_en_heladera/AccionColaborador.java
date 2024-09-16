package com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Heladera;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class AccionColaborador extends AccionHeladera {
    protected ColaboradorHumano responsable;

    protected AccionColaborador(LocalDateTime vFecha, Heladera vHeladera, ColaboradorHumano vResponsable) {
        super(vFecha, vHeladera);
        responsable = vResponsable;
    }
}