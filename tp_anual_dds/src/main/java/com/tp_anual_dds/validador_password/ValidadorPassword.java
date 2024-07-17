package com.tp_anual_dds.validador_password;

public class ValidadorPassword {
    private final CriterioLargo criterioLargo = new CriterioLargo(8, 64);
    private final CriterioTop10000MasComun criterioTop10000MasComun = new CriterioTop10000MasComun();

    public ValidadorPassword() {}

    public Boolean esValida(String contrasenia) {
        return criterioLargo.validar(contrasenia) && !(criterioTop10000MasComun.validar(contrasenia));
    }
}