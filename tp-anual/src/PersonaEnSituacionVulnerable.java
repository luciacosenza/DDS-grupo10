package src;

import java.time.LocalDate;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private TipoDocumento tipoDocumento;
    private String documento;
    private String domicilio;
    private Integer menoresACargo;

    enum TipoDocumento {
        // Completar
    }

    public PersonaEnSituacionVulnerable(String vNombre, LocalDate vFechaNacimiento, LocalDate vFechaRegistro, TipoDocumento vTipoDocumento, String vDocumento, String vDomicilio, Integer vMenoresACargo) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
        tipoDocumento = vTipoDocumento;
        documento = vDocumento;
        domicilio = vDomicilio;
        menoresACargo = vMenoresACargo;
    }

    public Integer menoresACargo() {
        return menoresACargo;
    }

    // darDeAlta()
    
    public Boolean poseeMenoresACargo() {
        return menoresACargo > 0;
    }
}
