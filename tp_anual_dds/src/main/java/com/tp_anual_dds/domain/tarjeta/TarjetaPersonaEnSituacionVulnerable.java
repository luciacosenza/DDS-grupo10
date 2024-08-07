package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;

public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {
    private PersonaEnSituacionVulnerable titular;
    protected ArrayList<UsoTarjeta> usos;

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

        // Programa la tarea para que se ejecute una vez por día
        timer.scheduleAtFixedRate(reseteoUsos, 0, periodo, unidad);
    }

    // Este método se ejecuta siempre que una Persona en Situación Vulnerable quiera realizar la Apertura de una Heladera (generalmente para retirar una Vianda)
    @Override
    public AperturaPersonaEnSituacionVulnerable intentarApertura(HeladeraActiva heladeraInvolucrada) {
        // Primero chequeo internamente que pueda realizar la Apertura
        heladeraInvolucrada.getGestorDeAperturas().revisarPermisoAperturaP(titular);

        LocalDateTime ahora = LocalDateTime.now();   // Guardo el valor en una variable para usar exactamente el mismo en las líneas de código posteriores

        AperturaPersonaEnSituacionVulnerable apertura = new AperturaPersonaEnSituacionVulnerable(ahora, heladeraInvolucrada, this.getTitular());
        apertura.darDeAlta();

        // Registro el Uso de la Tarjeta en la Heladera correspondiente
        UsoTarjeta uso = new UsoTarjeta(ahora, heladeraInvolucrada);
        agregarUso(uso);

        return apertura;
    }
}
