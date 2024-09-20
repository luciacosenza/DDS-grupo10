package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;

public interface ContribucionCreator {
    public Contribucion crearContribucionDefault(Colaborador colaborador, LocalDateTime fechaContribucion);

    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Boolean paraMigrar, Object... args);
}
