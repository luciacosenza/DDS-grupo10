package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("Apertura Colaborador")
@Getter
public class AperturaColaborador extends AccionColaborador {
    
    public AperturaColaborador(LocalDateTime vFecha, HeladeraActiva vHeladera, ColaboradorHumano vResponsable) {
        super(vFecha, vHeladera, vResponsable);
    }
}
