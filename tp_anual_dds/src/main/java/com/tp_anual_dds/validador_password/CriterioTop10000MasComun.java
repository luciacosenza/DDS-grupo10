package com.tp_anual_dds.validador_password;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CriterioTop10000MasComun extends CriterioValidacion {
    public CriterioTop10000MasComun() {}

    @Override
    public Boolean validar(String contrasenia) {
        try {
            return Files.lines(Paths.get("../../resources/10k-most-common.txt"))
                .anyMatch(linea -> linea.contains(contrasenia));
        } catch(Exception e) {
            // De ser necesario: System.out.println(e.getMessage());
            return false;
        }
    }
}
