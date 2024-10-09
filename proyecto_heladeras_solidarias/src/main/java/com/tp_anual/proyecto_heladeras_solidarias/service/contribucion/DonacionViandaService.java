package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionViandaRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class DonacionViandaService extends ContribucionService {

    private final ColaboradorRepository colaboradorRepository;
    private final DonacionViandaRepository donacionViandaRepository;

    public DonacionViandaService(ContribucionRepository vContribucionRepository, ColaboradorRepository vColaboradorRepository, DonacionViandaRepository vDonacionViandaRepository) {
        super(vContribucionRepository);
        colaboradorRepository = vColaboradorRepository;
        donacionViandaRepository = vDonacionViandaRepository;
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {
        ColaboradorHumano colaborador = colaboradorRepository.findById(colaboradorId);

        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorHumano colaborador = colaboradorRepository.findById(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionVianda.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        DonacionVianda donacionVianda = donacionViandaRepository.findById(contribucionId);
        ColaboradorHumano colaborador = colaboradorRepository.findById(colaboradorId);

        colaborador.sumarPuntos(donacionVianda.getMultiplicadorPuntos());
        colaboradorRepository.save(colaborador);
        confirmarSumaPuntos(contribucionId, colaboradorId, donacionVianda.getMultiplicadorPuntos());
    }
}
