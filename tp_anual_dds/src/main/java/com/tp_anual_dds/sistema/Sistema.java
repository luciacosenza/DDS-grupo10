package com.tp_anual_dds.sistema;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.Colaborador;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Sensor;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.tecnico.Tecnico;

public class Sistema {
    private static final ArrayList<Colaborador> colaboradores = new ArrayList<>();
    private static final ArrayList<Heladera> heladeras = new ArrayList<>();
    private static final ArrayList<PersonaEnSituacionVulnerable> personasEnSituacionVulnerable = new ArrayList<>();
    private static final ArrayList<Tecnico> tecnicos = new ArrayList<>();
    private static final ArrayList<Sensor> sensores = new ArrayList<>();

    public static ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public static ArrayList<Heladera> getHeladeras() {
        return heladeras;
    }

    public static ArrayList<PersonaEnSituacionVulnerable> getPersonasEnSituacionVulnerable() {
        return personasEnSituacionVulnerable;
    }

    public static ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public static ArrayList<Sensor> getSensores() {
        return sensores;
    }
    
    public static void agregarColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public static void eliminarColaborador(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public static void agregarHeladera(Heladera heladera) {
        heladeras.add(heladera);
    }

    public static void eliminarHeladera(Heladera heladera) {
        heladeras.remove(heladera);
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

    public static void agregarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public static void eliminarSensor(Sensor sensor) {
        sensores.remove(sensor);
    }
}
