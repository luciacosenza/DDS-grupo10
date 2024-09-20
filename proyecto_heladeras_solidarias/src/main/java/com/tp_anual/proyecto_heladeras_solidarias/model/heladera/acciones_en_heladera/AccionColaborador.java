package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import lombok.Getter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Acción Colaborador")
@Getter
public abstract class AccionColaborador extends AccionHeladera {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    protected ColaboradorHumano responsable;

    protected AccionColaborador(LocalDateTime vFecha, Heladera vHeladera, ColaboradorHumano vResponsable) {
        super(vFecha, vHeladera);
        responsable = vResponsable;
    }
}