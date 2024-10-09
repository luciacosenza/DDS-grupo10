package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.DonacionDineroRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class DonacionDineroService extends ContribucionService {

    private final DonacionDineroRepository donacionDineroRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // TODO: Probablemente no vaya acá el scheduler, porque los Services son Singletons

    public DonacionDineroService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, DonacionDineroRepository vDonacionDineroRepository) {
        super(vContribucionRepository, vColaboradorService);
        donacionDineroRepository = vDonacionDineroRepository;
    }

    public DonacionDinero obtenerDonacionDinero(Long donacionDineroId) {
        return donacionDineroRepository.findById(donacionDineroId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public DonacionDinero guardarDonacionDinero(DonacionDinero donacionDinero) {
        return donacionDineroRepository.save(donacionDinero);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.DonacionDinero.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        DonacionDinero donacionDinero = obtenerDonacionDinero(contribucionId);
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);

        if (donacionDinero.getFrecuencia() == DonacionDinero.FrecuenciaDePago.UNICA_VEZ) {
            Double puntosASumar = donacionDinero.getMonto() * donacionDinero.getMultiplicadorPuntos();
            colaborador.sumarPuntos(puntosASumar);
            colaboradorService.guardarColaborador(colaborador);
            confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumar);
        }

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = donacionDinero.getFrecuencia().unidad().between(donacionDinero.getUltimaActualizacion(), ahora);
            if (periodosPasados >= donacionDinero.getFrecuencia().periodo()) {  // TODO: Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a períodos pasados
                Double puntosASumarL = donacionDinero.getMonto() * donacionDinero.getMultiplicadorPuntos();
                colaborador.sumarPuntos(puntosASumarL);
                colaboradorService.guardarColaborador(colaborador);
                confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumarL);
                donacionDinero.setUltimaActualizacion(ahora);
                guardarDonacionDinero(donacionDinero);
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, donacionDinero.getPeriodoCalculoPuntos(), donacionDinero.getUnidadPeriodoCalculoPuntos());  // Ejecuta una vez por día (puede ser ineficiente en casos como mensual, semestral o anual)
    }
}
