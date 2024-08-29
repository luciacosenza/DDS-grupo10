package com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual.proyecto_heladeras_solidarias.domain.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class Tecnico {
    private static final Logger logger = Logger.getLogger(Tecnico.class.getName());
    private final PersonaFisica persona;
    private final String cuil;
    private MedioDeContacto medioDeContacto;    // TODO: Puede ser plural en un futuro
    private Area areaDeCobertura;
    private final ArrayList<Incidente> pendientes = new ArrayList<>();

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

    public void setMedioDeContacto(MedioDeContacto vMedioDeContacto) {
        medioDeContacto = vMedioDeContacto;
    }

    public void setAreaDeCobertura(Area vAreaDeCobertura) {
        areaDeCobertura = vAreaDeCobertura;
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

    public void registrarVisita(LocalDateTime fecha, String descripcion, String foto, Boolean estadoConsulta) {
        Incidente ultimoIncidenteTratado = pendientes.removeFirst();    // Suponemos que el Tecnico atiende los Incidentes por FIFO
        
        Visita visita = new Visita(this, ultimoIncidenteTratado, fecha, descripcion, foto, estadoConsulta);
        Sistema.getGestorDeVisitas().agregarVisita(visita);

        logger.log(Level.INFO, I18n.getMessage("tecnico.Tecnico.registrarVisita_info", ultimoIncidenteTratado.getHeladera().getNombre(), ultimoIncidenteTratado.getClass().getSimpleName(), persona.getNombre(2)));
    }

    // Como convención, para aproximar la Ubicación de un Técnico, vamos a usar el punto medio de su área de cobertura
    public Pair<Double,Double> ubicacionAprox() {
        return areaDeCobertura.puntoMedio();
    }
}
