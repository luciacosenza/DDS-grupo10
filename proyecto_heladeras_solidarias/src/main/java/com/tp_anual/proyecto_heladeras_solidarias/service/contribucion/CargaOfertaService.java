package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.CargaOfertaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

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
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {} // Esta Contribución no entra entre las que suman puntos

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {}  // Esta Contribución no entra entre las que suman puntos
}
