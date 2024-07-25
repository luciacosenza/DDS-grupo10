package com.tp_anual_dds.domain.tecnico;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.persona.PersonaFisica;
import com.tp_anual_dds.sistema.Sistema;

public class Tecnico {
    private final PersonaFisica persona;
    private final String cuil;
    private MedioDeContacto medioDeContacto;    // TODO Puede ser plural en un futuro
    private Area areaDeCobertura;
    private ArrayList<Incidente> pendientes = new ArrayList<>();

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

    public ArrayList<Incidente> getPendientes() {
        return pendientes;
    }

    public void darDeAlta() {
        Sistema.agregarTecnico(this);
    }

    public void darDeBaja() {
        Sistema.eliminarTecnico(this);
    }

    public void agregarAPendientes(Incidente incidente) {
        pendientes.add(incidente);
    }

    // TODO La lógica de este método puede cambiar al implementar el Broker
    public void registrarVisita(LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Incidente ultimoIncidenteTratado = pendientes.removeFirst();    // Suponemos que el Tecnico atiende los Incidentes por FIFO
        
        Visita visita = new Visita(this, ultimoIncidenteTratado, fecha, descripcion, foto, estadoConsulta);
        Sistema.getGestorVisitas().agregarVisita(visita);
    }

    // Como convención, para aproximar la Ubicación de un Técnico, vamos a usar el punto medio de su área de cobertura
    public Pair<Double,Double> ubicacionAprox() {
        return areaDeCobertura.puntoMedio();
    }
}
