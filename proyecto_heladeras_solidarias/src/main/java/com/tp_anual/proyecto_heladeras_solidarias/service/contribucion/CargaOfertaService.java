package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.CargaOfertaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log
public class CargaOfertaService extends ContribucionService {

    private final CargaOfertaRepository cargaOfertaRepository;

    public CargaOfertaService(ContribucionRepository vContribucionRepository, ColaboradorService vColaboradorService, CargaOfertaRepository vCargaOfertaRepository) {
        super(vContribucionRepository, vColaboradorService);
        cargaOfertaRepository = vCargaOfertaRepository;
    }

    public CargaOferta obtenerCargaOferta(Long cargaOfertaId) {
        return cargaOfertaRepository.findById(cargaOfertaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public CargaOferta guardarCargaOferta(CargaOferta cargaOferta) {
        return cargaOfertaRepository.save(cargaOferta);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // Estra Contribución tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {} // Esta Contribución no entra entre las que suman puntos

    // Programo la tarea para ejecutarse todos los días a las 00.00 hs
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    protected void calcularPuntos() {}  // Esta Contribución no entra entre las que suman puntos
}
