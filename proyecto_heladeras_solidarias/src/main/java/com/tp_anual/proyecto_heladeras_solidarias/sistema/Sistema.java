package com.tp_anual.proyecto_heladeras_solidarias.sistema;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.broker.Broker;
import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Sensor;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.domain.notificador.NotificadorDeEstado;
import com.tp_anual.proyecto_heladeras_solidarias.domain.notificador.NotificadorDeIncidentes;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.GestorDeSuscripciones;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.GestorDeVisitas;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Tecnico;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico.Visita;
import com.tp_anual.proyecto_heladeras_solidarias.reporte.ReporteIncidentesPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.reporte.ReporteMovimientosViandaPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.reporte.ReporteViandasPorColaborador;

public class Sistema {
    private static final ArrayList<Colaborador> colaboradores = new ArrayList<>();
    private static final ArrayList<HeladeraActiva> heladeras = new ArrayList<>();
    private static final ArrayList<Sensor> sensores = new ArrayList<>();
    private static final ArrayList<PersonaEnSituacionVulnerable> personasEnSituacionVulnerable = new ArrayList<>();
    private static final ArrayList<Tecnico> tecnicos = new ArrayList<>();
    private static final ArrayList<Visita> visitas = new ArrayList<>();
    private static final ArrayList<Oferta> ofertas = new ArrayList<>();
    private static final ArrayList<AccionHeladera> accionesHeladeras = new ArrayList<>();
    private static final ArrayList<Incidente> incidentes = new ArrayList<>();
    private static final Broker broker = new Broker();
    private static final GestorDeSuscripciones gestorDeSuscripciones = new GestorDeSuscripciones();
    private static final GestorDeVisitas gestorDeVisitas = new GestorDeVisitas();
    private static final NotificadorDeEstado notificadorDeEstado = new NotificadorDeEstado();
    private static final NotificadorDeIncidentes notificadorDeIncidentes = new NotificadorDeIncidentes();
    private static final ReporteIncidentesPorHeladera reporteIncidentesPorHeladera = new ReporteIncidentesPorHeladera();
    private static final ReporteMovimientosViandaPorHeladera reporteMovimientosViandaPorHeladera = new ReporteMovimientosViandaPorHeladera();
    private static final ReporteViandasPorColaborador reporteViandasPorColaborador = new ReporteViandasPorColaborador();
    
    public static ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public static ArrayList<HeladeraActiva> getHeladeras() {
        return heladeras;
    }

    public static ArrayList<Sensor> getSensores() {
        return sensores;
    }

    public static ArrayList<PersonaEnSituacionVulnerable> getPersonasEnSituacionVulnerable() {
        return personasEnSituacionVulnerable;
    }

    public static ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public static ArrayList<Visita> getVisitas() {
        return visitas;
    }

    public static ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public static ArrayList<AccionHeladera> getAccionesHeladeras() {
        return accionesHeladeras;
    }

    public static ArrayList<Incidente> getIncidentes() {
        return incidentes;
    }

    public static Broker getBroker() {
        return broker;
    }

    public static GestorDeSuscripciones getGestorDeSuscripciones() {
        return gestorDeSuscripciones;
    }

    public static GestorDeVisitas getGestorDeVisitas() {
        return gestorDeVisitas;
    }

    public static NotificadorDeEstado getNotificadorDeEstado() {
        return notificadorDeEstado;
    }

    public static NotificadorDeIncidentes getNotificadorDeIncidentes() {
        return notificadorDeIncidentes;
    }

    public static ReporteIncidentesPorHeladera getReporteIncidentesPorHeladera() {
        return reporteIncidentesPorHeladera;
    }

    public static ReporteMovimientosViandaPorHeladera getReporteMovimientosViandaPorHeladera() {
        return reporteMovimientosViandaPorHeladera;
    }
    
    public static ReporteViandasPorColaborador getReporteViandasPorColaborador() {
        return reporteViandasPorColaborador;
    }

    public static void agregarColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public static void eliminarColaborador(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public static void agregarHeladera(HeladeraActiva heladera) {
        heladeras.add(heladera);
    }

    public static void eliminarHeladera(HeladeraActiva heladera) {
        heladeras.remove(heladera);
    }

    public static void agregarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public static void eliminarSensor(Sensor sensor) {
        sensores.remove(sensor);
    }

    public static void agregarPersonaEnSituacionVulnerable(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        personasEnSituacionVulnerable.add(personaEnSituacionVulnerable);
    }

    public static void eliminarPersonaEnSituacionVulnerable(PersonaEnSituacionVulnerable personaEnSituacionVulnerable) {
        personasEnSituacionVulnerable.remove(personaEnSituacionVulnerable);
    }

    public static void agregarTecnico(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }

    public static void eliminarTecnico(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }

    public static void agregarVisita(Visita visita) {
        visitas.add(visita);
    }

    public static void eliminarVisita(Visita visita) {
        visitas.remove(visita);
    }
    
    public static void agregarOferta(Oferta oferta) {
        ofertas.add(oferta);
    }

    public static void eliminarOferta(Oferta oferta) {
        ofertas.remove(oferta);
    }

    public static void agregarAccionHeladera(AccionHeladera accionHeladera) {
        accionesHeladeras.add(accionHeladera);
    }

    public static void eliminarAccionHeladera(AccionHeladera accionHeladera) {
        accionesHeladeras.remove(accionHeladera);
    }

    public static void agregarIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }

    public static void eliminarIncidente(Incidente incidente) {
        incidentes.remove(incidente);
    }
}
