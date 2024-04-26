package src;

import java.util.Date;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private TipoDocumento tipoDocumento;
    private Integer documento;
    private String domicilio;
    private Integer menoresACargo;

    enum TipoDocumento {
        // Completar
    }

    public PersonaEnSituacionVulnerable(String vNombre, Date vFechaNacimiento, Date vFechaRegistro, TipoDocumento vTipoDocumento, Integer vDocumento, String vDomicilio, Integer vMenoresACargo) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
        tipoDocumento = vTipoDocumento;
        documento = vDocumento;
        domicilio = vDomicilio;
        menoresACargo = vMenoresACargo;
    }
}
