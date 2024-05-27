package com.tp_anual_dds.domain;

import java.time.LocalDateTime;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private LocalDateTime fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private Documento documento;
    private Ubicacion domicilio;
    private Integer menoresACargo;
    private Tarjeta tarjeta;    // tarjetaNuleada (ningun metodo hace algo pero los conoce, para tener polimorfismo)

    public PersonaEnSituacionVulnerable(String vNombre, LocalDateTime vFechaNacimiento, LocalDateTime vFechaRegistro, Documento vDocumento, Ubicacion vDomicilio, Integer vMenoresACargo, Tarjeta vTarjeta) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
        documento = vDocumento;
        domicilio = vDomicilio;
        menoresACargo = vMenoresACargo;
        tarjeta = null; // Temporal
    }

    public Integer getMenoresACargo() {
        return menoresACargo;
    }

    public void setTarjeta(Tarjeta tarjetaAsignada) {
        tarjeta = tarjetaAsignada;
    }

    public Boolean poseeMenoresACargo() {
        return menoresACargo > 0;
    }

    // darDeAlta()
}
