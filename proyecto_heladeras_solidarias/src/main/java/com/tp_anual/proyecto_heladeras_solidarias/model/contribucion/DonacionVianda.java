package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
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
public class DonacionVianda extends Contribucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "vianda_id")
    private final Vianda vianda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    private final HeladeraActiva heladera;

    @Transient
    private final Double multiplicadorPuntos = 1.5;

    public DonacionVianda(Colaborador vColaborador, LocalDateTime vFechaContribucion, Vianda vVianda, HeladeraActiva vHeladera) {
        super(vColaborador, vFechaContribucion);
        vianda = vVianda;
        heladera = vHeladera;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_vianda_comida", vianda.getComida()));
        System.out.println(I18n.getMessage("contribucion.DonacionVianda.obtenerDetalles_out_vianda_comida", heladera.getNombre()));
    }
    
    @Override
    public void validarIdentidad() {
        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionVianda.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos() {
        colaborador.sumarPuntos(multiplicadorPuntos);
        confirmarSumaPuntos(multiplicadorPuntos);
    }
}