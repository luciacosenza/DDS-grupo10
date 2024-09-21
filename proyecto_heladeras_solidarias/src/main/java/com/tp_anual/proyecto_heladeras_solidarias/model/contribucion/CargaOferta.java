package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@Entity
@Log
@Getter
@Setter
public class CargaOferta extends Contribucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oferta_id")
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