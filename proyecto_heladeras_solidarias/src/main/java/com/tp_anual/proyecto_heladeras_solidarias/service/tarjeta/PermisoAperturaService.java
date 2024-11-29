package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.PermisoAperturaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaColaboradorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Service
@Log
public class PermisoAperturaService {

    private final PermisoAperturaRepository permisoAperturaRepository;
    private final TarjetaColaboradorRepository tarjetaColaboradorRepository;

    @Getter
    private final Long retrasoRevocacion;

    public PermisoAperturaService(PermisoAperturaRepository vPermisoAperturaRepository, TarjetaColaboradorRepository vTarjetaColaboradorRepository, @Value("#{3}") /* 3 horas en horas */ Long vRetrasoRevocacion) {
        permisoAperturaRepository = vPermisoAperturaRepository;
        tarjetaColaboradorRepository = vTarjetaColaboradorRepository;
        retrasoRevocacion = vRetrasoRevocacion;
    }

    public PermisoApertura obtenerPermisoApertura(Long permisoAperturaId){
        return permisoAperturaRepository.findById(permisoAperturaId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public PermisoApertura guardarPermisoApertura(PermisoApertura permisoApertura) {
        return permisoAperturaRepository.save(permisoApertura);
    }

    public PermisoApertura crearPermisoApertura(Heladera heladeraPermitida, LocalDateTime fechaOtorgamiento) {
        PermisoApertura permisoApertura = new PermisoApertura(heladeraPermitida, fechaOtorgamiento, true);
        Long permisoAperturaId = guardarPermisoApertura(permisoApertura).getId();   // Guardo el permiso para obtener el id
        programarRevocacionPermiso(permisoAperturaId);

        return permisoApertura;
    }

    public TarjetaColaborador obtenerTarjetaColaboradorPorPermisoApertura(Long permisoAperturaId) {
        return tarjetaColaboradorRepository.findByPermisosId(permisoAperturaId);
    }

    public Boolean esHeladeraPermitida(Long permisoAperturaId, Heladera heladera) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        return heladera == permisoApertura.getHeladeraPermitida();
    }

    public void revocarPermisoApertura(Long permisoAperturaId) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        permisoApertura.revocarPermiso();
        guardarPermisoApertura(permisoApertura);

        log.log(Level.INFO, I18n.getMessage("tarjeta.PermisoApertura.revocarPermisoApertura_info", permisoApertura.getHeladeraPermitida().getNombre(), obtenerTarjetaColaboradorPorPermisoApertura(permisoAperturaId).getTitular().getPersona().getNombre(2)));
    }

    // Programo la revocación del permiso
    // No es la mejor forma, pero se dificultaba el uso de un Task Scheduler
    @Async
    public void programarRevocacionPermiso(Long permisoAperturaId) {
        try {
            TimeUnit.HOURS.sleep(retrasoRevocacion);
            revocarPermisoApertura(permisoAperturaId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.log(Level.SEVERE, I18n.getMessage("tarjeta.PermisoApertura.revocarPermisoApertura_err"));
        }
    }
}
