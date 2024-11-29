package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.PermisoApertura;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.PermisoAperturaService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class GestorDeAperturas {
    private final HeladeraService heladeraService;
    private final PermisoAperturaService permisoAperturaService;

    public GestorDeAperturas(HeladeraService vHeladeraService, PermisoAperturaService vPermisoAperturaService) {
        heladeraService = vHeladeraService;
        permisoAperturaService = vPermisoAperturaService;
    }
    
    public void revisarMotivoApertura(Heladera heladera, MotivoSolicitud motivo) {
        if (motivo == MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION &&
            (heladeraService.estaVacia(heladera.getId()) || heladeraService.estaraVacia(heladera.getId()))) {
            
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"));
        }

        if ((motivo == MotivoSolicitud.INGRESAR_DONACION ||
            motivo == MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION) &&
            heladeraService.estaLlena(heladera.getId()) || heladeraService.estaraLlena(heladera.getId())) {

            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_err_heladera_llena", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_llena"));
        }
    }
    
    public void revisarPermisoAperturaC(Heladera heladera, ColaboradorHumano colaborador) {
        TarjetaColaborador tarjetaColaborador = colaborador.getTarjeta();
        List<PermisoApertura> permisos = tarjetaColaborador.getPermisos();

        PermisoApertura permisoARevisar = permisos.stream()
            .filter(permiso -> permisoAperturaService.esHeladeraPermitida(permiso.getId(), heladera))
            .max(Comparator.comparing(PermisoApertura::getFechaOtorgamiento))
            .orElse(null);

        if (permisoARevisar == null) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_err", colaborador.getPersona().getNombre(2), heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaOtorgamiento = permisoARevisar.getFechaOtorgamiento();
        long horasPasadas = heladera.getUnidadTiempoPermiso().between(fechaOtorgamiento, ahora);

        if (horasPasadas >= heladera.getTiempoPermiso()) {
            permisoAperturaService.revocarPermisoApertura(permisoARevisar.getId());


            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_err", colaborador.getPersona().getNombre(2), heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaC_exception"));
        }
    }

    public void revisarPermisoAperturaP(Heladera heladera) {
        if(heladeraService.estaVacia(heladera.getId())) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.GestorDeAperturas.resvisarPermisoAperturaP_err_heladera_vacia", heladera.getNombre()));
            throw new UnsupportedOperationException(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"));
        }
    }
}