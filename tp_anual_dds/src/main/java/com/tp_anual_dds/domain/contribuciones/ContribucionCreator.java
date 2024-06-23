package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.Colaborador;

public interface ContribucionCreator {
    public Contribucion crearContribucion(Colaborador colaborador, LocalDateTime fechaContribucion, Object... args);
}
