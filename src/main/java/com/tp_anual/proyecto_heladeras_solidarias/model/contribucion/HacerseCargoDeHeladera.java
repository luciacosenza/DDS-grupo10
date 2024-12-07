package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.Locale;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Entity
@NamedNativeQuery(
    name = "HacerseCargoDeHeladera.findHacerseCargoDeHeladeraParaPuntos",
    query = "SELECT * FROM hacerse_cargo_de_heladera AS h " +
            "WHERE CURRENT_DATE - h.ultimaActualizacion >= 30)"
)
@Log
@Getter
public class HacerseCargoDeHeladera extends Contribucion {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "heladera")
    private Heladera heladeraObjetivo;  // final

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private LocalDateTime ultimaActualizacion;

    public HacerseCargoDeHeladera() {
        super();
    }

    public HacerseCargoDeHeladera(Colaborador vColaborador, LocalDateTime vFechaContribucion, Heladera vHeladeraObjetivo) {
        super(vColaborador, vFechaContribucion);
        heladeraObjetivo = vHeladeraObjetivo;
        ultimaActualizacion = LocalDateTime.now();
    }

    @Override
    public void obtenerDetalles() {
        super.obtenerDetalles();

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("contribucion.HacerseCargoDeHeladera.obtenerDetalles_out_heladera_objetivo", new Object[] {heladeraObjetivo.getNombre()}, Locale.getDefault());

        System.out.println(logMessage);
    }
}