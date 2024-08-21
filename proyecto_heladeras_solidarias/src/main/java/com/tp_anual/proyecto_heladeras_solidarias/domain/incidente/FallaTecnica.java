package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;

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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }
}
