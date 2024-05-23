package domain;

import java.time.LocalDate;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaRegistro;
    private Documento documento;
    private Ubicacion domicilio;
    private Integer menoresACargo;
    private Tarjeta tarjeta;    // tarjetaNuleada (ningun metodo hace algo pero los conoce, para tener polimorfismo)

    public PersonaEnSituacionVulnerable(String vNombre, LocalDate vFechaNacimiento, LocalDate vFechaRegistro, Documento vDocumento, Ubicacion vDomicilio, Integer vMenoresACargo, Tarjeta vTarjeta) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
        documento = vDocumento;
        domicilio = vDomicilio;
        menoresACargo = vMenoresACargo;
        tarjeta = null; // Temporal
    }

    public Integer menoresACargo() {
        return menoresACargo;
    }

    public void tarjeta(Tarjeta tarjetaAsignada) {
        tarjeta = tarjetaAsignada;  // A chequear
    }

    // darDeAlta()
    
    public Boolean poseeMenoresACargo() {
        return menoresACargo > 0;
    }
}
