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
@DiscriminatorValue("Viandas Maximas")
@Log
@Getter
@Setter
public class SuscripcionViandasMax extends Suscripcion {
    
    @NotNull
    @Min(value = 0)
    private Integer viandasParaLlenarMax;

    public SuscripcionViandasMax(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, MedioDeContacto vMedioDeContactoElegido, Integer vViandasParaLlenarMax) {
        super(vColaborador, vHeladera, vMedioDeContactoElegido);
        viandasParaLlenarMax = vViandasParaLlenarMax;
    }
}