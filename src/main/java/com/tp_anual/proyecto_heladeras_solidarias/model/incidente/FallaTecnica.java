package com.tp_anual.proyecto_heladeras_solidarias.model.incidente;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@NamedNativeQuery(
        name = "FallaTecnica.findFallasTecnicasParaTecnico",
        query = "SELECT * FROM falla_tecnica AS f " +
                "WHERE f.id IN (" +
                "SELECT f0.id FROM falla_tecnica AS f0 " +
                "INNER JOIN tecnico AS t2 " +
                "ON f0.tecnico = t2.id " +
                "WHERE t2.id = :tecnico)",
        resultClass =  FallaTecnica.class
)
@Getter
public class FallaTecnica extends Incidente {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    private Colaborador colaborador;    // final

    private String descripcion; // final
    
    private String foto;    // final

    public FallaTecnica() {
        super();
    }

    public FallaTecnica(LocalDateTime vFecha, Heladera vHeladera, Colaborador vColaborador, String vDescripcion, String vFoto) {
        super(vFecha, vHeladera);
        colaborador = vColaborador;
        descripcion = vDescripcion;
        foto = vFoto;
    }
}
