package com.tp_anual.proyecto_heladeras_solidarias.validador_password;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.CriterioLargo;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.CriterioTop10000MasComun;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.CriterioValidacion;
import com.tp_anual.proyecto_heladeras_solidarias.service.validador_password.ValidadorPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidadorPasswordTest {

    @Autowired
    ValidadorPassword validadorPassword;

    @Test
    @DisplayName("Testeo que se encuentre en la lista de 10k-most-common passwords")
    public void MostCommonTest() {
        String contrasenia = "fuckyou";

        Assertions.assertFalse(validadorPassword.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea corta")
    public void CortaTest() {
        String contrasenia = "uwu";

        Assertions.assertFalse(validadorPassword.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea larga")
    public void LargoTest() {
        String contrasenia = "1234567891011121314151617181920kfsaofnhsaijvfnhewidsmoiiojgvmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnmnergvnerivjeruhghghghghghghghghghghghghghghghghghghghghghghghghghghg.xdlolazomemeafsagfedfvdwvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfvfw";

        Assertions.assertFalse(validadorPassword.esValida(contrasenia));
    }

    @Test
    @DisplayName("Testeo que sea válida")
    public void ValidaTest() {
        String contrasenia = "SNGFDSJidsnfds.549fwqe";

        Assertions.assertTrue(validadorPassword.esValida(contrasenia));
    }
}