package com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    Colaborador findByUsuario_Username(String username);

    @Query(nativeQuery = true, name = "Colaborador.findColaboradorParaEMail")
    Colaborador findColaboradorParaEMail(@Param("email") String eMail);
}
