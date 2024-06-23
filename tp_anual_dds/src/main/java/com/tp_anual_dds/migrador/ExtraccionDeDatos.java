package com.tp_anual_dds.migrador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.conversores.ConversorFormaContribucion;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.ContribucionCreator;

public abstract class ExtraccionDeDatos implements ExtraccionDeDatosStrategy {
    @Override
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
