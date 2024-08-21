package com.tp_anual.proyecto_heladeras_solidarias.validador_password;

public class CriterioLargo extends CriterioValidacion {
    private final Integer longitudMinima;
    private final Integer longitudMaxima;
    
    public CriterioLargo(Integer vLongitudMinima, Integer vLongitudMaxima) {
        longitudMinima = vLongitudMinima;
        longitudMaxima = vLongitudMaxima;
    }

    @Override
    public Boolean validar(String contrasenia) {
        return ((!esCorta(contrasenia)) && (!esLarga(contrasenia)));
    }

    public Boolean esCorta(String contrasenia) {
        return contrasenia.length() < longitudMinima;
    }

    public Boolean esLarga(String contrasenia) {
        return contrasenia.length() > longitudMaxima;
    }
}
