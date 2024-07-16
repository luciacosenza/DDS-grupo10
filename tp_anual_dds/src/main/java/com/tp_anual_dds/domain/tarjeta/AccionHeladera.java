package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.sistema.Sistema;

public class AccionHeladera {
    private TipoAccion tipo;
    private LocalDateTime fecha;
    private Heladera heladera;
    private ColaboradorHumano colaborador;

    public enum TipoAccion {
        SOLICITUD_APERTURA,
        APERTURA
    }

    public AccionHeladera(TipoAccion vTipo, LocalDateTime vFecha, Heladera vHeladera, ColaboradorHumano vColaborador) {
        tipo = vTipo;
        fecha = vFecha;
        heladera = vHeladera;
        colaborador = vColaborador;
    }

    public void darDeAlta() {
        Sistema.agregarAccionHeladera(this);
    }

    public void darDeBaja() {
        Sistema.eliminarAccionHeladera(this);
    }
}
