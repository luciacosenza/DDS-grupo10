package domain;

import java.time.LocalDate;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private Documento documento;
    private Ubicacion domicilio;
    private Integer menoresACargo;

    public PersonaEnSituacionVulnerable(String vNombre, LocalDate vFechaNacimiento, LocalDate vFechaRegistro, Documento vDocumento, Ubicacion vDomicilio, Integer vMenoresACargo) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
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
