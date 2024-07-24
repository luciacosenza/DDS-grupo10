package com.tp_anual_dds.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.sistema.Sistema;

public abstract class Incidente {
    protected LocalDateTime fecha;
    protected HeladeraActiva heladera;

    public HeladeraActiva getHeladera() {
        return heladera;
    }

    // La lógica de este método puede cambiar al implementar el Broker (TODO)
    public void darDeAlta() {
        Sistema.agregarIncidente(this);

        Sistema.getAlertador().alertarDe(this); // Pusimos esta línea en este método para centralizar el llamado del Alertador (que no tenga que ser llamado por Colaboradores y Heladeras), pero puede cambiar al implementar el Broker (TODO)
    }

    public void darDeBaja() {
        Sistema.eliminarIncidente(this);
    }
}
