package com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Viandas Minimas")
@Log
@Getter
@Setter
public class SuscripcionViandasMin extends Suscripcion {
    
    private Integer viandasDisponiblesMin;

    public SuscripcionViandasMin(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, MedioDeContacto vMedioDeContactoElegido, Integer vViandasDisponiblesMin) {
        super(vColaborador, vHeladera, vMedioDeContactoElegido);
        viandasDisponiblesMin = vViandasDisponiblesMin;
    }
}