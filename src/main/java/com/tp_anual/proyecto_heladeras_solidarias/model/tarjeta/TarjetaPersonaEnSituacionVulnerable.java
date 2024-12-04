package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.util.ArrayList;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
@Getter
public class TarjetaPersonaEnSituacionVulnerable extends Tarjeta {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_en_situacion_vulnerable")
    @Setter
    private PersonaEnSituacionVulnerable titular;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    protected List<UsoTarjeta> usos;   // final

    public TarjetaPersonaEnSituacionVulnerable() {
        super();
    }

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
}
