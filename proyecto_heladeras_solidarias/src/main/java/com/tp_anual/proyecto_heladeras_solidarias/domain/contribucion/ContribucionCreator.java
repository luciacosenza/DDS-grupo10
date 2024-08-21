package com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;

public interface ContribucionCreator {
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args);
}
