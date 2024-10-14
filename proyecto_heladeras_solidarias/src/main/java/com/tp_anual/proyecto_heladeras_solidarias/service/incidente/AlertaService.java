package com.tp_anual.proyecto_heladeras_solidarias.service.incidente;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.repository.incidente.AlertaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeIncidentes;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

@Service
@Log
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final NotificadorDeIncidentes notificadorDeIncidentes;

    public AlertaService(AlertaRepository vAlertaRepository, NotificadorDeIncidentes vNotificadorDeIncidentes) {
        alertaRepository = vAlertaRepository;
        notificadorDeIncidentes = vNotificadorDeIncidentes;
    }

    public Alerta obtenerAlerta(Long idAlerta) {
        return alertaRepository.findById(idAlerta).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<Alerta> obtenerAlertas() {
        return new ArrayList<>(alertaRepository.findAll());
    }

    public Alerta guardarAlerta(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    public void producirAlerta(LocalDateTime fecha, Heladera heladera, Alerta.TipoAlerta tipo) {
        Alerta alerta = new Alerta(LocalDateTime.now(), heladera, tipo);
        Long alertaId = guardarAlerta(alerta).getId();
        notificadorDeIncidentes.notificarIncidente(alertaId);

        log.log(Level.INFO, I18n.getMessage("incidente.Alerta.producirAlerta_info", alerta.getTipo(), heladera.getNombre()));
    }
}
