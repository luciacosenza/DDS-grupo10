package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionViandaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class DonacionViandaService extends ContribucionService {

    private final DonacionViandaRepository donacionViandaRepository;
    private final Double multiplicadorPuntos = 1.5;

    public DonacionViandaService(DonacionViandaRepository vDonacionViandaRepository) {
        super();
        donacionViandaRepository = vDonacionViandaRepository;
    }

    @Override
    public DonacionVianda obtenerContribucion(Long donacionViandaId) {
        return donacionViandaRepository.findById(donacionViandaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<DonacionVianda> obtenerDonacionesViandaQueSumanPuntos() {
        return new ArrayList<>(donacionViandaRepository.findByYaSumoPuntosFalse());
    }

    @Override
    public DonacionVianda guardarContribucion(Contribucion donacionVianda) {
        return donacionViandaRepository.save((DonacionVianda) donacionVianda);
    }

    @Override
    public void validarIdentidad(Long donacionViandaId, Colaborador colaborador) {
        if (colaborador.getDomicilio() == null) {
            log.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_err", colaborador.getPersona().getNombre(2)));
            throw new IllegalArgumentException(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"));
        }
    }

    @Override
    protected void confirmarSumaPuntos(Long donacionViandaId, Colaborador colaborador, Double puntosSumados) {
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionVianda.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {
        ArrayList<DonacionVianda> donacionesVianda = obtenerDonacionesViandaQueSumanPuntos();

        for (DonacionVianda donacionVianda : donacionesVianda) {
            Colaborador colaborador = donacionVianda.getColaborador();
            colaborador.sumarPuntos(multiplicadorPuntos);

            donacionVianda.sumoPuntos();
            guardarContribucion(donacionVianda);    // Al guardar la contribución, se guarda el colaborador por cascada
            confirmarSumaPuntos(donacionVianda.getId(), colaborador, multiplicadorPuntos);
        }
    }
}
