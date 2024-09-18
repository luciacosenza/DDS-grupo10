package com.tp_anual.proyecto_heladeras_solidarias.domain.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter
@Setter
public class FallaTecnica extends Incidente {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    private final Colaborador colaborador;

    private final String descripcion;
    
    private final String foto;

    public FallaTecnica(LocalDateTime vFecha, HeladeraActiva vHeladera, Colaborador vColaborador, String vDescripcion, String vFoto) {
        fecha = vFecha;
        heladera = vHeladera;
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
