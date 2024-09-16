package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import lombok.Getter;

@Getter
public class FallaTecnica extends Incidente {
    private final Colaborador colaborador;
    private final String descripcion;
    private final String foto;

    public FallaTecnica(LocalDateTime vFecha, HeladeraActiva vHeladera, Colaborador vColaborador, String vDescripcion, String vFoto) {
        fecha = vFecha;
        heladera = vHeladera;
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
