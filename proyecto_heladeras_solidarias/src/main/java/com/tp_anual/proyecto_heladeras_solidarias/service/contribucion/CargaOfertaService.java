package com.tp_anual.proyecto_heladeras_solidarias.service.contribucion;

import com.tp_anual.proyecto_heladeras_solidarias.repository.contribucion.ContribucionRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class CargaOfertaService extends ContribucionService {

    public CargaOfertaService(ContribucionRepository vContribucionRepository) {
        super(vContribucionRepository);
    }

    @Override
    public void validarIdentidad(Long contribucionId, Long colaboradorId) {}   // No tiene ningún requisito en cuanto a los datos o identidad del colaborador

    @Override
    protected void confirmarSumaPuntos(Long contribucionId, Long colaboradorId, Double puntosSumados) {} // Esta Contribución no entra entre las que suman puntos

    @Override
    protected void calcularPuntos(Long contribucionId, Long colaboradorId) {}  // Esta Contribución no entra entre las que suman puntos
}
