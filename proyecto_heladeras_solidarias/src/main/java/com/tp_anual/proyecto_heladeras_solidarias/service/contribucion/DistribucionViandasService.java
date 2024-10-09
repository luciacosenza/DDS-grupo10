package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DistribucionViandasRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class DistribucionViandasService extends ContribucionService {

    private final DistribucionViandasRepository distribucionViandasRepository;

    public DistribucionViandasService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, DistribucionViandasRepository vDistribucionViandasRepository) {
        super(vContribucionRepository, vColaboradorService);
        distribucionViandasRepository = vDistribucionViandasRepository;
    }

    public DistribucionViandas obtenerDistribucionViandas(Long distribucionViandasId) {
        return distribucionViandasRepository.findById(distribucionViandasId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public DistribucionViandas guardarDistribucionViandas(DistribucionViandas distribucionViandas) {
        return distribucionViandasRepository.save(distribucionViandas);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        if(colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.DistribucionViandas.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        DistribucionViandas distribucionViandas = obtenerDistribucionViandas(contribucionId);
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        Double puntosASumar = Double.valueOf(distribucionViandas.getCantidadViandasAMover()) * distribucionViandas.getMultiplicadorPuntos();
        colaborador.sumarPuntos(puntosASumar);
        colaboradorService.guardarColaborador(colaborador);
        confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumar);
    }
}
