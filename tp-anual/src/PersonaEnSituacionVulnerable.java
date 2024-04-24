package src;

import java.util.Date;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private tipoDoc tipoDocumento;
    private Integer documento;
    private String domicilio;
    private Integer menoresACargo;

    enum tipoDoc {
        // Completar
    }

    public PersonaEnSituacionVulnerable(String nombr, Date fechaN, Date fechaR, tipoDoc tipoD, Integer docum, String domic, Integer menoA) {
        nombre = nombr;
        fechaNacimiento = fechaN;
        fechaRegistro = fechaR;
        tipoDocumento = tipoD;
        documento = docum;
        domicilio = domic;
        menoresACargo = menoA;
    }
}
