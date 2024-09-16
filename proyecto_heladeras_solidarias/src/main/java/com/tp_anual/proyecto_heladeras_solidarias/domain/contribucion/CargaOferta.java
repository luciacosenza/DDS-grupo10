package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public class CargaOferta extends Contribucion {
    private final Oferta oferta;

    public CargaOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        super(vColaborador, vFechaContribucion);
        oferta = vOferta;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.CargaOferta.obtenerDetalles_out", oferta.getNombre()));
    }

    @Override
    public void validarIdentidad() {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {} // Esta Contribución no entra entre las que suman puntos

    @Override
    protected void calcularPuntos() {}  // Esta Contribución no entra entre las que suman puntos


}