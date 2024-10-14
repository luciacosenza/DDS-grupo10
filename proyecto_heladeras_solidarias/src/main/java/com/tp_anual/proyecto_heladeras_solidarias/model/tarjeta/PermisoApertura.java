package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

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
    @JoinColumn(name = "heladera")
    private Heladera heladeraPermitida;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaOtorgamiento;

    private Boolean otorgado;

    public PermisoApertura(Heladera vHeladeraPermitida, LocalDateTime vFechaOtorgamiento, Boolean vOtorgado) {
        heladeraPermitida = vHeladeraPermitida;
        fechaOtorgamiento = vFechaOtorgamiento;
        otorgado = vOtorgado;
    }

    public void actualizarFechaOtorgamiento(){
        setFechaOtorgamiento(LocalDateTime.now());
    }
}