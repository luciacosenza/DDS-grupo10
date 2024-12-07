package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.Locale;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;


@Entity
@Log
@Getter
public class CargaOferta extends Contribucion {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oferta")
    private Oferta oferta;  // final

    public CargaOferta() {}

    public CargaOferta(Colaborador vColaborador, LocalDateTime vFechaContribucion, Oferta vOferta) {
        super(vColaborador, vFechaContribucion);
        oferta = vOferta;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("contribucion.CargaOferta.obtenerDetalles_out", new Object[] {oferta.getNombre()}, Locale.getDefault());

        System.out.println(logMessage);
    }
}