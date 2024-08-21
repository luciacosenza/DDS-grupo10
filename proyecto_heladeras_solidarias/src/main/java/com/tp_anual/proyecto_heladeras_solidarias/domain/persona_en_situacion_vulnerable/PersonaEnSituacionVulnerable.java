package com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class PersonaEnSituacionVulnerable {
    private final PersonaFisica persona;
    private Ubicacion domicilio;
    private final LocalDateTime fechaRegistro;
    private Integer menoresACargo;
    private TarjetaPersonaEnSituacionVulnerable tarjeta;

    public PersonaEnSituacionVulnerable(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaNacimiento, Ubicacion vDomicilio, LocalDateTime vFechaRegistro, Integer vMenoresACargo) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        domicilio = vDomicilio;
        fechaRegistro = vFechaRegistro;
        menoresACargo = vMenoresACargo;
    }

    public PersonaFisica getPersona() {
        return persona;
    }

    public Ubicacion getDomicilio() {
        return domicilio;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
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

    public void darDeAlta() {
        Sistema.agregarPersonaEnSituacionVulnerable(this);
    }

    public void darDeBaja() {
        Sistema.eliminarPersonaEnSituacionVulnerable(this);
    }
}