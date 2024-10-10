package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.GeneradorCodigo;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private PersonaEnSituacionVulnerable titular;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "uso_tarjeta_id")
    @NotNull
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
