package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionDineroRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class DonacionDineroService extends ContribucionService {

    private final ColaboradorRepository colaboradorRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public DonacionDineroService(ContribucionRepository vContribucionRepository, ColaboradorRepository vColaboradorRepository, DonacionDineroRepository vDonacionDineroRepository) {
        super(vContribucionRepository);
        colaboradorRepository = vColaboradorRepository;
        donacionDineroRepository = vDonacionDineroRepository;
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionDinero.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        DonacionDinero donacionDinero = donacionDineroRepository.findById(contribucionId);
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId);

        if (donacionDinero.getFrecuencia() == DonacionDinero.FrecuenciaDePago.UNICA_VEZ) {
            Double puntosASumar = donacionDinero.getMonto() * donacionDinero.getMultiplicadorPuntos();
            colaborador.sumarPuntos(puntosASumar);
            colaboradorRepository.save(colaborador);
            confirmarSumaPuntos(colaboradorId, puntosASumar);
        }

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = donacionDinero.getFrecuencia().unidad().between(donacionDinero.getUltimaActualizacion(), ahora);
            if (periodosPasados >= donacionDinero.getFrecuencia().periodo()) {  // TODO: Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a períodos pasados
                Double puntosASumarL = donacionDinero.getMonto() * donacionDinero.getMultiplicadorPuntos();
                colaborador.sumarPuntos(puntosASumarL);
                colaboradorRepository.save(colaborador);
                confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumarL);
                donacionDinero.setUltimaActualizacion(ahora);
                donacionDineroRepository.save(donacionDinero);
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, donacionDinero.getPeriodoCalculoPuntos(), donacionDinero.getUnidadPeriodoCalculoPuntos());  // Ejecuta una vez por día (puede ser ineficiente en casos como mensual, semestral o anual)
    }
}
