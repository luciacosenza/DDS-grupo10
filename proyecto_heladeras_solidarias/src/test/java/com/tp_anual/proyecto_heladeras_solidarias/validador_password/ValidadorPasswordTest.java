package com.tp_anual.proyecto_heladeras_solidarias.validador_password;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorPasswordTest {

    @Test
    @DisplayName("Testeo que se encuentre en la lista de 10k-most-common passwords")
    public void MostCommonTest() {
        String contrasenia = "fuckyou";
        
        CriterioTop10000MasComun criterioTop10000MasComun = new CriterioTop10000MasComun();
        ArrayList<CriterioValidacion> criterios = new ArrayList<>();
        criterios.add(criterioTop10000MasComun); 
        ValidadorPassword validador = new ValidadorPassword(criterios);

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea corta")
    public void CortaTest() {
        String contrasenia = "uwu";

        CriterioLargo criterioLargo = new CriterioLargo(8, 64);
        ArrayList<CriterioValidacion> criterios = new ArrayList<>();
        criterios.add(criterioLargo); 
        ValidadorPassword validador = new ValidadorPassword(criterios);

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea larga")
    public void LargoTest() {
        String contrasenia = "1234567891011121314151617181920kfsaofnhsaijvfnhewidsmoiiojgvmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnergvnerivjeruhghghghghghghghghghghghghghghghghghghghghghghghghghghg.xdlolazomemeafsagfedfvdwvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfw";

        CriterioLargo criterioLargo = new CriterioLargo(8, 64);
        ArrayList<CriterioValidacion> criterios = new ArrayList<>();
        criterios.add(criterioLargo); 
        ValidadorPassword validador = new ValidadorPassword(criterios);

        Assertions.assertFalse(validador.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea válida")
    public void ValidaTest() {
        String contrasenia = "SNGFDSJidsnfds.549fwqe";

        CriterioTop10000MasComun criterioTop10000MasComun = new CriterioTop10000MasComun();
        CriterioLargo criterioLargo = new CriterioLargo(8, 64);
        ArrayList<CriterioValidacion> criterios = new ArrayList<>();
        criterios.add(criterioTop10000MasComun);
        criterios.add(criterioLargo); 
        ValidadorPassword validador = new ValidadorPassword(criterios);

        Assertions.assertTrue(validador.esValida(contrasenia));
    }
}