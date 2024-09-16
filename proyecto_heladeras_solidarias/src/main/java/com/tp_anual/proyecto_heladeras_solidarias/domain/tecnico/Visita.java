package com.tp_anual.proyecto_heladeras_solidarias.domain.tecnico;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import lombok.Getter;

@Getter
public class Visita {
    private final Tecnico tecnico;
    private final Incidente incidente;
    private final LocalDateTime fecha;
    private final String descripcion;
    private final String foto;
    private final Boolean estado;

    public Visita(Tecnico vTecnico, Incidente vIncidente, LocalDateTime vFecha, String vDescripcion, String vFoto, Boolean vExitosa) {
        tecnico = vTecnico;
        incidente = vIncidente;
        fecha = vFecha;
        descripcion = vDescripcion;
        foto = vFoto;
        estado = vExitosa;
    }

    public void darDeAlta() {
        Sistema.agregarVisita(this);
    }

    public void darDeBaja() {
        Sistema.eliminarVisita(this);
    }
}
