package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.PermisoAperturaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.repository.tarjeta.TarjetaColaboradorRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Service
@Log
public class PermisoAperturaService {

    private final PermisoAperturaRepository permisoAperturaRepository;
    private final TarjetaColaboradorRepository tarjetaColaboradorRepository;
    private final HeladeraService heladeraService;

    @Getter
    private final Long retrasoRevocacion;

    private final I18nService i18nService;

    public PermisoAperturaService(PermisoAperturaRepository vPermisoAperturaRepository, TarjetaColaboradorRepository vTarjetaColaboradorRepository, HeladeraService vHeladeraService, @Value("#{3}") /* 3 horas en horas */ Long vRetrasoRevocacion, I18nService vI18nService) {
        permisoAperturaRepository = vPermisoAperturaRepository;
        tarjetaColaboradorRepository = vTarjetaColaboradorRepository;
        heladeraService = vHeladeraService;
        retrasoRevocacion = vRetrasoRevocacion;

        i18nService = vI18nService;
    }

    public PermisoApertura obtenerPermisoApertura(Long permisoAperturaId){
        return permisoAperturaRepository.findById(permisoAperturaId).orElseThrow(() -> new EntityNotFoundException(i18nService.getMessage("obtenerEntidad_exception")));
    }

    public PermisoApertura guardarPermisoApertura(PermisoApertura permisoApertura) {
        return permisoAperturaRepository.save(permisoApertura);
    }

    public PermisoApertura crearPermisoApertura(Heladera heladeraPermitida, LocalDateTime fechaOtorgamiento, SolicitudAperturaColaborador.MotivoSolicitud motivo, Integer cantidadViandas) {
        PermisoApertura permisoApertura = new PermisoApertura(heladeraPermitida, fechaOtorgamiento, motivo, cantidadViandas, true);
        Long permisoAperturaId = guardarPermisoApertura(permisoApertura).getId();   // Guardo el permiso para obtener el id
        programarRevocacionPermiso(permisoAperturaId);

        return permisoApertura;
    }

    public TarjetaColaborador obtenerTarjetaColaboradorPorPermisoApertura(Long permisoAperturaId) {
        return tarjetaColaboradorRepository.findByPermisosId(permisoAperturaId);
    }

    public PermisoApertura obtenerUnPermisoParaTarjetaYHeladera(Heladera heladera, TarjetaColaborador tarjeta) {
        return permisoAperturaRepository.findPermisoParaTarjetaAndHeladera(heladera.getId(), tarjeta.getCodigo());
    }

    public Boolean esHeladeraPermitida(Long permisoAperturaId, Heladera heladera) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        return heladera == permisoApertura.getHeladeraPermitida();
    }

    public void revocarPermisoApertura(Long permisoAperturaId) {
        PermisoApertura permisoApertura = obtenerPermisoApertura(permisoAperturaId);
        permisoApertura.revocarPermiso();
        guardarPermisoApertura(permisoApertura);

        log.log(Level.INFO, i18nService.getMessage("tarjeta.PermisoApertura.revocarPermisoApertura_info", permisoApertura.getHeladeraPermitida().getNombre(), obtenerTarjetaColaboradorPorPermisoApertura(permisoAperturaId).getTitular().getPersona().getNombre(2)));

        Heladera heladera = permisoApertura.getHeladeraPermitida();

        // Rollbackeo la reserva de viandas/espacio cuando ya se perdieron los permisos sobre esa operación (y no va a poder ser realizada)
        switch(permisoApertura.getMotivo()) {

            case INGRESAR_DONACION -> heladeraService.reservarViandas(heladera.getId(), 1);

            case INGRESAR_LOTE_DE_DISTRIBUCION -> heladeraService.reservarViandas(heladera.getId(), permisoApertura.getCantidadViandas());

            case RETIRAR_LOTE_DE_DISTRIBUCION -> heladeraService.reservarEspacioParaViandas(heladera.getId(), permisoApertura.getCantidadViandas());

            default -> {}
        }
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
            log.log(Level.SEVERE, i18nService.getMessage("tarjeta.PermisoApertura.revocarPermisoApertura_err"));
        }
    }
}
