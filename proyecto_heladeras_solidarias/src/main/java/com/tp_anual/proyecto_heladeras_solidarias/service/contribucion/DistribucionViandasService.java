package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DistribucionViandasRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class DistribucionViandasService extends ContribucionService {

    private final DistribucionViandasRepository distribucionViandasRepository;
    private final Double multiplicadorPuntos = 1d;

    public DistribucionViandasService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, DistribucionViandasRepository vDistribucionViandasRepository) {
        super(vContribucionRepository, vColaboradorService);
        distribucionViandasRepository = vDistribucionViandasRepository;
    }

    public DistribucionViandas obtenerDistribucionViandas(Long distribucionViandasId) {
        return distribucionViandasRepository.findById(distribucionViandasId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public ArrayList<DistribucionViandas> obtenerDistribucionesViandasQueSumanPuntos() {
        return new ArrayList<>(distribucionViandasRepository.findByYaSumoPuntosFalse());
    }

    public DistribucionViandas guardarDistribucionViandas(DistribucionViandas distribucionViandas) {
        return distribucionViandasRepository.save(distribucionViandas);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.DistribucionViandas.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        ArrayList<DistribucionViandas> distribucionesViandas = obtenerDistribucionesViandasQueSumanPuntos();

        for (DistribucionViandas distribucionViandas : distribucionesViandas) {
            Double puntosASumar = Double.valueOf(distribucionViandas.getCantidadViandasAMover()) * multiplicadorPuntos;
            ColaboradorHumano colaborador = (ColaboradorHumano) distribucionViandas.getColaborador();

            colaborador.sumarPuntos(puntosASumar);
            colaboradorService.guardarColaborador(colaborador);

            distribucionViandas.setYaSumoPuntos(true);
            guardarDistribucionViandas(distribucionViandas);
            confirmarSumaPuntos(distribucionViandas.getId(), colaborador.getId(), puntosASumar);
        }
    }
}
