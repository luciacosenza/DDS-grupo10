package com.tp_anual_dds.validador_password;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorPasswordTest {

    @Test
    @DisplayName("Testeo que se encuentre en la lista de 10k-most-common passwords")
    public void MostCommonTest() {
        String contrasenia = "fuckyou";
        ValidadorPassword validador = new ValidadorPassword();

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea corta")
    public void CortaTest() {
        String contrasenia = "uwu";
        ValidadorPassword validador = new ValidadorPassword();

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea larga")
    public void LargoTest() {
        String contrasenia = "1234567891011121314151617181920kfsaofnhsaijvfnhewidsmoiiojgvmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnergvnerivjeruhghghghghghghghghghghghghghghghghghghghghghghghghghghg.xdlolazomemeafsagfedfvdwvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfw";
        ValidadorPassword validador = new ValidadorPassword();

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea válida")
    public void ValidaTest() {
        String contrasenia = "SNGFDSJidsnfds.549fwqe";
        ValidadorPassword validador = new ValidadorPassword();

        Assertions.assertTrue(validador.esValida(contrasenia));
    }
}