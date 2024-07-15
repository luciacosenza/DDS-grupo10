package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.persona.PersonaFisica;
import com.tp_anual_dds.sistema.Sistema;

public class Tecnico {
    private PersonaFisica persona;
    private String cuil;
    private MedioDeContacto medioDeContacto;
    private Area areaDeCobertura;

    public Tecnico(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaNacimiento, String vCuil, MedioDeContacto vMedioDeContacto, Area vAreaDeCobertura) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }

    public void darDeAlta() {
        Sistema.agregarTecnico(this);
    }

    public void darDeBaja() {
        Sistema.eliminarTecnico(this);
    }

    public void registrarVisita(LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Visita visita = new Visita(this, fecha, descripcion, foto, estadoConsulta);
        visita.registrar();
    }
}
