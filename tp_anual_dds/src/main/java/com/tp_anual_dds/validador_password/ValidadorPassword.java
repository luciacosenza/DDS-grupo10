package com.tp_anual_dds.validador_password;

import java.util.ArrayList;

public class ValidadorPassword {
    private final ArrayList<CriterioValidacion> criterios;

    public ValidadorPassword(ArrayList<CriterioValidacion> vCriterios) {
        criterios = vCriterios;
    }

    // Este método valida que la contraseña cumpla, para cada Criterio pedido
    public Boolean esValida(String contrasenia) {
        return criterios.stream().allMatch(criterio -> criterio.validar(contrasenia));
    }
}