package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
@Getter
@Setter
public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_en_situacion_vulnerable_id")
    private PersonaEnSituacionVulnerable titular;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    protected final ArrayList<UsoTarjeta> usos;

    public TarjetaPersonaEnSituacionVulnerable(String vCodigo, PersonaEnSituacionVulnerable vTitular) {
        super(vCodigo);
        titular = vTitular;
        usos = new ArrayList<>();
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
}
