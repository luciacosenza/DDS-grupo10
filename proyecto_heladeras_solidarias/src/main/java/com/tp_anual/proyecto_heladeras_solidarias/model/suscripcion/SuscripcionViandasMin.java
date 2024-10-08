package com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@DiscriminatorValue("Viandas Minimas")
@Log
@Getter
@Setter
public class SuscripcionViandasMin extends Suscripcion {
    
    @NotNull
    @Min(value = 0)
    private Integer viandasDisponiblesMin;

    public SuscripcionViandasMin(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, MedioDeContacto vMedioDeContactoElegido, Integer vViandasDisponiblesMin) {
        super(vColaborador, vHeladera, vMedioDeContactoElegido);
        viandasDisponiblesMin = vViandasDisponiblesMin;
    }
}