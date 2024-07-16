package com.tp_anual_dds.validador_password;

public class CriterioLargo extends CriterioValidacion {
    public CriterioLargo() {}

    @Override
    public Boolean validar(String contrasenia) {
        return (contrasenia.length() >= 8 && contrasenia.length() <= 64);
    }
}
