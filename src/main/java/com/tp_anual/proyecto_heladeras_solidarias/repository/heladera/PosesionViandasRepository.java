package com.tp_anual.proyecto_heladeras_solidarias.repository.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.PosesionViandas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosesionViandasRepository extends JpaRepository<PosesionViandas, Long> {

    PosesionViandas findFirstByColaboradorOrderByFechaDesc(ColaboradorHumano colaborador);
}