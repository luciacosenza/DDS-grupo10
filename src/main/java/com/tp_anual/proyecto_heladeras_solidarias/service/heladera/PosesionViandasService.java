package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.PosesionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.PosesionViandasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PosesionViandasService {

    private final PosesionViandasRepository posesionViandasRepository;

    public PosesionViandasService(PosesionViandasRepository vPosesionViandasRepository) {
        posesionViandasRepository = vPosesionViandasRepository;
    }

    public PosesionViandas obtenerPosesionViandas(Long posesionViandasId) {
        return posesionViandasRepository.findById(posesionViandasId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public PosesionViandas obtenerPosesionViandasMasRecientePorColaborador(ColaboradorHumano colaborador) {
        return posesionViandasRepository.findFirstByColaboradorOrderByFechaDesc(colaborador);
    }

    public PosesionViandas guardarPosesionViandas(PosesionViandas posesionViandas) {
        return posesionViandasRepository.save(posesionViandas);
    }

    public PosesionViandas crearPosesionViandas(ColaboradorHumano colaborador, List<Vianda> viandas, LocalDateTime fecha) {
        return new PosesionViandas(colaborador, viandas, fecha);
    }
}
