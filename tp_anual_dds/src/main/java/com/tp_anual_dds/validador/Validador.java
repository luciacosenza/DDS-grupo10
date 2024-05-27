package com.tp_anual_dds.validador;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Validador{

    public Validador() {}

    private Boolean largoContraseniaValida(String contrasenia) {
        return (contrasenia.length() >= 8 && contrasenia.length() <= 64);
    }

    private Boolean esTop10000MalaContrasenia(String contrasenia) {
        try {
            return Files.lines(Paths.get("./resources/10k-most-common.txt")).anyMatch(linea -> linea.contains(contrasenia));
        } catch(Exception e) {
            // De ser necesario: System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean esValida(String contrasenia) {
        return this.largoContraseniaValida(contrasenia) && !(this.esTop10000MalaContrasenia(contrasenia));
    }
}