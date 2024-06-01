package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.ColaboradorHumano;
import com.tp_anual_dds.domain.Contribucion;
import com.tp_anual_dds.domain.ContribucionFactory;
import com.tp_anual_dds.domain.ConversorFormaContribucion;

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
        ContribucionFactory factory = ConversorFormaContribucion.convertirStrAContribucionFactory(formaContribucionStr);
        return factory.crearContribucion(colaborador, fechaContribucion); // Posible error al querer crear una contribucion a traves de una factory sin pasarle el resto de argumentos necesarios
    }
}
