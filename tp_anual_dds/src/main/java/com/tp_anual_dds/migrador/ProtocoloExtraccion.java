package com.tp_anual_dds.migrador;

import com.tp_anual_dds.conversores.ConversorFormaContribucion;
import com.tp_anual_dds.domain.ColaboradorHumano;
import com.tp_anual_dds.domain.Contribucion;
import com.tp_anual_dds.domain.ContribucionCreator;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class ProtocoloExtraccion implements ProtocoloExtraccionStrategy {
    public abstract ArrayList<ColaboradorHumano> extract(String archivo);

    protected abstract ColaboradorHumano procesarColaborador(String[] datos);

    protected String quitarEspacios(String string) {
        string = string.replaceAll("\\s+", "");
        return string;
    }

    protected String quitarNumericosYEspeciales(String string) {
        string = string.replaceAll("[^a-zA-Z]", "");
        return string;
    }

    protected Contribucion registrarContribucion(String formaContribucionStr, ColaboradorHumano colaborador, LocalDateTime fechaContribucion) {
        ContribucionCreator creator = ConversorFormaContribucion.convertirStrAContribucionCreator(formaContribucionStr);
        return creator.crearContribucion(colaborador, fechaContribucion); // Posible error al querer crear una contribucion a traves de un Creator sin pasarle el resto de argumentos necesarios
    }
}
