package src;

import java.util.ArrayList;

public class Tarjeta {
    private String codigo;
    private PersonaEnSituacionVulnerable titular;
    private ArrayList<UsoTarjeta> usos;

    public Tarjeta(String vCodigo, PersonaEnSituacionVulnerable vTitular, ArrayList<UsoTarjeta> vUsos) {
        codigo = vCodigo;
        titular = vTitular;
        usos = vUsos;
    }
}