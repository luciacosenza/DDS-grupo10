package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.java.Log;

@Entity
@Log
@Getter
public class DonacionVianda extends Contribucion {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vianda")
    private Vianda vianda;  // final

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    private Heladera heladera;    // final

    public DonacionVianda() {
        super();
    }

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda, Heladera vHeladera) {
        super(vColaborador, vFechaContribucion);
        vianda = vVianda;
        heladera = vHeladera;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_vianda_comida", vianda.getComida()));
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_heladera", heladera.getNombre()));
    }
}