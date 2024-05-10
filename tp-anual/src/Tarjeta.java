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

    // usar()
    
    public Integer cantidadUsos() {
        return 4 + 2 * titular.menoresACargo();
    }

    public void resetearUsos() {
        usos.clear();
    }
}