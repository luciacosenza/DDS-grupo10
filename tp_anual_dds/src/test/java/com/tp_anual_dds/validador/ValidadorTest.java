package com.tp_anual_dds.validador;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorTest {

    @Test
    @DisplayName("Testeo que este en la lista de 10k passwords")
    public void FedeTest() {
        String contrasenia = "fuckyou";
        Validador prueba = new Validador();

        Assertions.assertFalse(prueba.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea corta")
    public void CortaTest() {
        String contrasenia = "uwu";
        Validador prueba = new Validador();

        Assertions.assertFalse(prueba.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea larga")
    public void LargoTest() {
        String contrasenia = "1234567891011121314151617181920kfsaofnhsaijvfnhewidsmoiiojgvmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnergvnerivjeruhghghghghghghghghghghghghghghghghghghghghghghghghghghg.xdlolazomemeafsagfedfvdwvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfw";
        Validador prueba = new Validador();

        Assertions.assertFalse(prueba.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea valida")
    public void ValidTest() {
        String contrasenia = "SNGFDSJidsnfds.549fwqe";
        Validador prueba = new Validador();

        Assertions.assertTrue(prueba.esValida(contrasenia));
    }
}