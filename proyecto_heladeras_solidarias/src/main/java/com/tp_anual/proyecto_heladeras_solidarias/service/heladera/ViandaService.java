package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;


import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.ViandaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class ViandaService {

    private final ViandaRepository viandaRepository;

    public ViandaService(ViandaRepository vViandaRepository) {
        viandaRepository = vViandaRepository;
    }

    public Vianda obtenerVianda(Long viandaId) {
       return viandaRepository.findById(viandaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    public Vianda guardarVianda(Vianda vianda) {
        return viandaRepository.save(vianda);
    }
}
