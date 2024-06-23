package com.tp_anual_dds.domain.persona_en_situacion_vulnerable;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class PersonaEnSituacionVulnerable {
    private String nombre;
    private LocalDateTime fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private Documento documento;
    private Ubicacion domicilio;
    private Integer menoresACargo;
    private TarjetaPersonaEnSituacionVulnerable tarjeta;    // tarjetaNuleada (ningun metodo hace algo pero los conoce, para tener polimorfismo)

    public PersonaEnSituacionVulnerable(String vNombre, LocalDateTime vFechaNacimiento, LocalDateTime vFechaRegistro, Documento vDocumento, Ubicacion vDomicilio, Integer vMenoresACargo) {
        nombre = vNombre;
        fechaNacimiento = vFechaNacimiento;
        fechaRegistro = vFechaRegistro;
        documento = vDocumento;
        domicilio = vDomicilio;
        menoresACargo = vMenoresACargo;
    }

    public Integer getMenoresACargo() {
        return menoresACargo;
    }

    public TarjetaPersonaEnSituacionVulnerable getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaPersonaEnSituacionVulnerable tarjetaAsignada) {
        tarjeta = tarjetaAsignada;
    }

    public Boolean poseeMenoresACargo() {
        return menoresACargo > 0;
    }

    // darDeAlta()
}
