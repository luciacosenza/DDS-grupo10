package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.repository.colaborador.ColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.HacerseCargoDeHeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

@Service
@Log
public class HacerseCargoDeHeladeraService extends ContribucionService {

    private final HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // TODO: Probablemente no vaya acá el scheduler, porque los Services son Singletons

    public HacerseCargoDeHeladeraService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, HacerseCargoDeHeladeraRepository vHacerseCargoDeHeladeraRepository) {
        super(vContribucionRepository, vColaboradorService);
        hacerseCargoDeHeladeraRepository = vHacerseCargoDeHeladeraRepository;
    }

    public HacerseCargoDeHeladera obtenerHacerseCargoDeHeladera(Long hacerseCargoId) {
        return hacerseCargoDeHeladeraRepository.findById(hacerseCargoId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public HacerseCargoDeHeladera guardarHacerseCargoDeHeladera(HacerseCargoDeHeladera hacerseCargoDeHeladera) {
        return hacerseCargoDeHeladeraRepository.save(hacerseCargoDeHeladera);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {
        ColaboradorJuridico colaborador = colaboradorService.obtenerColaboradorJuridico(colaboradorId);
        log.log(Level.INFO, I18n.getMessage("contribucion.HacerseCargoDeHeladera.confirmarSumaPuntos_info", puntosSumados, colaborador.getPersona().getNombre(2)), getClass().getSimpleName());
    }

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {
        HacerseCargoDeHeladera hacerseCargoDeHeladera = obtenerHacerseCargoDeHeladera(contribucionId);
        ColaboradorJuridico colaborador = colaboradorService.obtenerColaboradorJuridico(colaboradorId);

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            long mesesPasados = ChronoUnit.MONTHS.between(hacerseCargoDeHeladera.getUltimaActualizacion(), ahora);
            if (mesesPasados >= 1 && hacerseCargoDeHeladera.getHeladeraObjetivo().getEstado()) {    // Dado que en el test nos dimos cuenta que puede fallar por milésimas, podríamos pensar en restarle un segundo, por ejemplo, a meses pasados (TODO)
                Double puntosASumar = hacerseCargoDeHeladera.getMultiplicadorPuntos();
                colaborador.sumarPuntos(puntosASumar);
                colaboradorService.guardarColaborador(colaborador);
                confirmarSumaPuntos(contribucionId, colaboradorId, puntosASumar);
                hacerseCargoDeHeladera.setUltimaActualizacion(ahora);
                guardarHacerseCargoDeHeladera(hacerseCargoDeHeladera);
            }
        };

        // Programa la tarea para que se ejecute una vez por día
        scheduler.scheduleAtFixedRate(calculoPuntos, 0, hacerseCargoDeHeladera.getPeriodoCalculoPuntos(), hacerseCargoDeHeladera.getUnidadPeriodoCalculoPuntos());  // Ejecuta una vez por día (puede ser ineficiente)
    }
}
