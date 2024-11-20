package com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    Colaborador findByUsuario_Username(String username);
}
