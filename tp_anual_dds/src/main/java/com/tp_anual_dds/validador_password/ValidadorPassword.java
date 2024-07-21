package com.tp_anual_dds.validador_password;

import java.util.ArrayList;

public class ValidadorPassword {
    private final ArrayList<CriterioValidacion> criterios;

    public ValidadorPassword(ArrayList<CriterioValidacion> vCriterios) {
        criterios = vCriterios;
    }

    public Boolean esValida(String contrasenia) {
        return criterios.stream().noneMatch(criterio -> !criterio.validar(contrasenia));
    }
}