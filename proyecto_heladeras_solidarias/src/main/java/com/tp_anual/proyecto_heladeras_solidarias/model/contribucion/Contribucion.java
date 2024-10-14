package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Log
@Getter
@Setter
public abstract class Contribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    protected final Colaborador colaborador;

    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime fechaContribucion;
    
    protected Boolean completada;

    protected Boolean yaSumoPuntos;

    protected Contribucion(Colaborador vColaborador, LocalDateTime vFechaContribucion) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        completada = false;
        yaSumoPuntos = false;
    }

    public void marcarComoCompletada() {
        setCompletada(true);
    }

    public void sumoPuntos() {
        setYaSumoPuntos(true);
    }

    public void seCompletoYSumoPuntos() {
        marcarComoCompletada();
        sumoPuntos();
    }

    public void obtenerDetalles() {
        System.out.println(I18n.getMessage("contribucion.Contribucion.obtenerDetalles_out_nombre", getClass().getSimpleName()));
        System.out.println(I18n.getMessage("contribucion.Contribucion.obtenerDetalles_out_colaborador_title"));
        colaborador.obtenerDetalles();
        
        if (fechaContribucion != null)
            System.out.println(I18n.getMessage("contribucion.Contribucion.obtenerDetalles_out_fecha_contribucion", fechaContribucion));
    }
}
