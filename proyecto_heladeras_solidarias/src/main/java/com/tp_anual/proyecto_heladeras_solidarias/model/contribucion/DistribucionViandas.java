package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;

@Entity
@Log
@Getter
@Setter
public class DistribucionViandas extends Contribucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id; 
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private final HeladeraActiva origen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private final HeladeraActiva destino;

    @NotNull
    @Min(value = 0)
    private final Integer cantidadViandasAMover;

    @Enumerated(EnumType.STRING)
    @NotNull
    private final MotivoDistribucion motivo;

    @Transient
    private final Double multiplicadorPuntos = 1d;

    public enum MotivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }
    
    public DistribucionViandas(Colaborador vColaborador, LocalDateTime vFechaContribucion, HeladeraActiva vOrigen, HeladeraActiva vDestino, Integer vCantidadViandasAMover, MotivoDistribucion vMotivo) {
        super(vColaborador, vFechaContribucion);
        origen = vOrigen;
        destino = vDestino;
        cantidadViandasAMover = vCantidadViandasAMover;
        motivo = vMotivo;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_origen", origen.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_destino", destino.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_candidad_viandas_a_mover", cantidadViandasAMover));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_motivo", motivo));
    }
    
    @Override
    public void validarIdentidad() {
        if(colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.DistribucionViandas.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos() {
        Double puntosASumar = Double.valueOf(cantidadViandasAMover) * multiplicadorPuntos;
        colaborador.sumarPuntos(puntosASumar);
        confirmarSumaPuntos(puntosASumar);
    }
}
