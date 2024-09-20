package com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Desperfecto")
@Log
@Getter
@Setter
puclic class SuscripcionViandasMax extends Suscripcion {
    
    public SuscripcionViandasMax(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, MedioDeContacto vMedioDeContactoElegido, Integer vViandasParaLlenarMax) {
        super(vColaborador, vHeladera, vMedioContactoElegido);
    }
}