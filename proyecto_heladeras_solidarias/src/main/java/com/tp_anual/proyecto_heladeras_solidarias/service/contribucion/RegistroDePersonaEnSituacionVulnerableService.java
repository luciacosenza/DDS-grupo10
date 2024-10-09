package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.RegistroDePersonaEnSituacionVulnerableRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.logging.Level;

@Service
@Log
public class RegistroDePersonaEnSituacionVulnerableService extends ContribucionService {

    private final RegistroDePersonaEnSituacionVulnerableRepository registroDePersonaEnSituacionVulnerableRepository;

    public RegistroDePersonaEnSituacionVulnerableService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, RegistroDePersonaEnSituacionVulnerableRepository vRegistroDePersonaEnSituacionVulnerableRepository) {
        super(vContribucionRepository, vColaboradorService);
        registroDePersonaEnSituacionVulnerableRepository = vRegistroDePersonaEnSituacionVulnerableRepository;
    }

    public RegistroDePersonaEnSituacionVulnerable obtenerRegistroDePersonaEnSituacionVulnerable(Long registroId) {
        return registroDePersonaEnSituacionVulnerableRepository.findById(registroId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public RegistroDePersonaEnSituacionVulnerable guardarRegistroDePersonaEnSituacionVulnerable(RegistroDePersonaEnSituacionVulnerable registro) {
        return registroDePersonaEnSituacionVulnerableRepository.save(registro);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        if(colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable = obtenerRegistroDePersonaEnSituacionVulnerable(contribucionId);
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        registroDePersonaEnSituacionVulnerable.getTarjetaAsignada().programarReseteoUsos();
        registroDePersonaEnSituacionVulnerableRepository.save(registroDePersonaEnSituacionVulnerable);

        log.log(Level.INFO, I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        RegistroDePersonaEnSituacionVulnerable registroDePersonaEnSituacionVulnerable = obtenerRegistroDePersonaEnSituacionVulnerable(contribucionId);
        ColaboradorHumano colaborador = colaboradorService.obtenerColaboradorHumano(colaboradorId);

        Double puntosASumar = registroDePersonaEnSituacionVulnerable.getMultiplicadorPuntos();
        colaborador.sumarPuntos(puntosASumar);
        colaboradorService.guardarColaborador(colaborador);
        confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumar);
    }
}
