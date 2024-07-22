package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.persona.PersonaFisica;
import com.tp_anual_dds.sistema.Sistema;

public class Tecnico {
    private final PersonaFisica persona;
    private String cuil;
    private MedioDeContacto medioDeContacto;    // Puede ser plural en un futuro
    private Area areaDeCobertura;

    public Tecnico(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaNacimiento, String vCuil, MedioDeContacto vMedioDeContacto, Area vAreaDeCobertura) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }

    public PersonaFisica getPersona() {
        return persona;
    }

    public String getCuil() {
        return cuil;
    }

    public MedioDeContacto getMedioDeContacto() {
        return medioDeContacto;
    }

    public Area getAreaDeCobertura() {
        return areaDeCobertura;
    }

    public void darDeAlta() {
        Sistema.agregarTecnico(this);
    }

    public void darDeBaja() {
        Sistema.eliminarTecnico(this);
    }

    public void registrarVisita(LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Visita visita = new Visita(this, fecha, descripcion, foto, estadoConsulta);
        visita.darDeAlta();
    }
}
