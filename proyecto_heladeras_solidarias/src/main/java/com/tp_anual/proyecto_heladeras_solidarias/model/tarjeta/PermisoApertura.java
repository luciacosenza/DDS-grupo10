package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@Log
public class PermisoApertura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private HeladeraActiva heladeraPermitida;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime fechaOtorgamiento;

    @NotNull
    private Boolean otorgado;

    public PermisoApertura(HeladeraActiva vHeladeraPermitida, LocalDateTime vFechaOtorgamiento, Boolean vOtorgado) {
        heladeraPermitida = vHeladeraPermitida;
        fechaOtorgamiento = vFechaOtorgamiento;
        otorgado = vOtorgado;
    }

    public void actualizarFechaOtorgamiento(){
        setFechaOtorgamiento(LocalDateTime.now());
    }

    public Boolean esHeladeraPermitida(HeladeraActiva heladera) {
        return heladera == heladeraPermitida;
    }
}