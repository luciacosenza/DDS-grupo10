package com.tp_anual_dds.validador_password;

public class ValidadorPassword {
    private final CriterioLargo criterioLargo = new CriterioLargo();
    private final CriterioTop10000Mala criterioTop10000Mala = new CriterioTop10000Mala();

    public ValidadorPassword() {}

    public Boolean esValida(String contrasenia) {
        return criterioLargo.validar(contrasenia) && !(criterioTop10000Mala.validar(contrasenia));
    }
}