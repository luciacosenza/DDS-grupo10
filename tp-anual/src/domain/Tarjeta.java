package domain;

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

    public PersonaEnSituacionVulnerable getTitular() {
        return titular;
    }

    // usar()

    public Integer cantidadUsos() {
        return 4 + 2 * titular.getMenoresACargo();
    }

    public void resetearUsos() {
        usos.clear();
    }
}