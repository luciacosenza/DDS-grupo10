package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {
    protected ArrayList<UsoTarjeta> usos;
    private PersonaEnSituacionVulnerable titular;

    public TarjetaPersonaEnSituacionVulnerable(PersonaEnSituacionVulnerable vTitular) {
        codigo = GeneradorCodigo.generarCodigo(false);
        titular = vTitular;
        usos = new ArrayList<>();
    }

    @Override
    public PersonaEnSituacionVulnerable getTitular() {
        return titular;
    }

    public void setTitular(PersonaEnSituacionVulnerable vTitular) {
        titular = vTitular;
    }
    
    public void agregarUso(UsoTarjeta uso) {
        usos.add(uso);
    }

    public void resetUsos() {
        usos.clear();
    }

    public Integer cantidadUsos() {
        return usos.size();
    }

    @Override
    public Boolean puedeUsar() {
        return cantidadUsos() < 4 + 2 * titular.getMenoresACargo();
    }

    public void programarReseteoUsos() {
        Integer periodo = 1;
        TimeUnit unidad = TimeUnit.DAYS;
        
        Runnable reseteoUsos = () -> {
            resetUsos();
        };

        // Programa la tarea para que se ejecute una vez por dia
        timer.scheduleAtFixedRate(reseteoUsos, 0, periodo, unidad);
    }

    @Override
    public void intentarApertura(HeladeraActiva heladeraInvolucrada) {
        if(!puedeUsar()) {
            throw new UnsupportedOperationException("Ya agotó los usos diarios de su Tarjeta");
        }

        AperturaPersonaEnSituacionVulnerable apertura = new AperturaPersonaEnSituacionVulnerable(LocalDateTime.now(), heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();
    }
}
