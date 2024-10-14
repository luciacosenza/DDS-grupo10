package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class FallaTecnica extends Incidente {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    private final Colaborador colaborador;

    private final String descripcion;
    
    private final String foto;

    public FallaTecnica(LocalDateTime vFecha, Heladera vHeladera, Colaborador vColaborador, String vDescripcion, String vFoto) {
        super(vFecha, vHeladera);
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
