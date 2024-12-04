package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.Getter;

@Entity
@Log
@Getter
public class DistribucionViandas extends Contribucion {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_origen")
    private Heladera origen;    // final

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_destino")
    private Heladera destino; // final

    @Min(value = 0)
    private Integer cantidadViandasAMover;  // final

    @Enumerated(EnumType.STRING)
    private  MotivoDistribucion motivo; // final

    @Setter
    private Boolean retiroRealizado;

    public enum MotivoDistribucion {
        DESPERFECTO_EN_LA_HELADERA,
        FALTA_DE_VIANDAS_EN_DESTINO
    }

    public DistribucionViandas() {
        super();
    }
    
    public DistribucionViandas(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vOrigen, Heladera vDestino, Integer vCantidadViandasAMover, MotivoDistribucion vMotivo) {
        super(vColaborador, vFechaContribucion);
        origen = vOrigen;
        destino = vDestino;
        cantidadViandasAMover = vCantidadViandasAMover;
        motivo = vMotivo;
        retiroRealizado = false;
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_origen", origen.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_destino", destino.getNombre()));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_candidad_viandas_a_mover", cantidadViandasAMover));
        System.out.println(I18n.getMessage("contribucion.DistribucionViandas.obtenerDetalles_out_motivo", motivo));
    }

    public void confirmarRetiro() {setRetiroRealizado(true);}

}
