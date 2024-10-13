package com.tp_anual.proyecto_heladeras_solidarias.service.heladera;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.AlertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.FallaTecnicaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeEstado;
import com.tp_anual.proyecto_heladeras_solidarias.service.notificador.NotificadorDeIncidentes;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.*;
import com.tp_anual.proyecto_heladeras_solidarias.repository.heladera.HeladeraRepository;
import com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion.GestorDeSuscripciones;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

@Service
@Log
public class HeladeraService {
    private final HeladeraRepository heladeraRepository;
    private final ViandaService viandaService;
    private final MedioDeContactoService medioDeContactoService;
    private final ColaboradorService colaboradorService;
    private final AlertaService alertaService;
    private final FallaTecnicaService fallaTecnicaService;
    private final GestorDeSuscripciones gestorDeSuscripciones;
    private final NotificadorDeEstado notificadorDeEstado;
    private final NotificadorDeIncidentes notificadorDeIncidentes;

    public HeladeraService(HeladeraRepository vHeladeraRepository, ViandaService vViandaService, MedioDeContactoService vMedioDeContactoService, ColaboradorService vColaboradorService, AlertaService vAlertaService, FallaTecnicaService vFallaTecnicaService, GestorDeSuscripciones vGestorDeSuscripciones, NotificadorDeEstado vNotificadorDeEstado, NotificadorDeIncidentes vNotificadorDeIncidentes) {
        heladeraRepository = vHeladeraRepository;
        viandaService = vViandaService;
        medioDeContactoService = vMedioDeContactoService;
        colaboradorService = vColaboradorService;
        alertaService = vAlertaService;
        fallaTecnicaService = vFallaTecnicaService;
        gestorDeSuscripciones = vGestorDeSuscripciones;
        notificadorDeEstado = vNotificadorDeEstado;
        notificadorDeIncidentes = vNotificadorDeIncidentes;
    }

    public Heladera obtenerHeladera(Long heladeraId) {
        return (Heladera) heladeraRepository.findById(heladeraId).orElseThrow(() -> new EntityNotFoundException(I18n.getMessage("obtenerEntidad_exception")));
    }

    public ArrayList<Heladera> obtenerHeladeras(){
        return new ArrayList<>(heladeraRepository.findAll());
    }

    public Heladera guardarHeladera(Heladera heladera) {
        return heladeraRepository.save(heladera);
    }

    public Boolean estaVacia(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        return heladera.getViandas().isEmpty();
    }

    public Boolean estaLlena(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        return Objects.equals(heladera.viandasActuales(), heladera.getCapacidad());
    }

    public Boolean verificarCapacidad(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        return heladera.viandasActuales() < heladera.getCapacidad();
    }

    public void verificarCondiciones(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        ArrayList<Suscripcion> suscripciones = gestorDeSuscripciones.suscripcionesPorHeladera(heladeraId);

        for (Suscripcion suscripcion : suscripciones) {

            switch(suscripcion) {

                case SuscripcionViandasMin suscripcionViandasMin -> {
                    // Verifico si se está vaciando
                    if (heladera.viandasActuales() <= suscripcionViandasMin.getViandasDisponiblesMin())
                        reportarEstadoSegunCondicionSuscripcion(heladeraId, suscripcion.getMedioDeContactoElegido().getId(), Suscripcion.CondicionSuscripcion.VIANDAS_MIN);
                }

                case SuscripcionViandasMax suscripcionViandasMax -> {
                    // Verifico si se está llenando
                    if ((heladera.getCapacidad() - heladera.viandasActuales()) <= suscripcionViandasMax.getViandasParaLlenarMax())
                        reportarEstadoSegunCondicionSuscripcion(heladeraId, suscripcion.getMedioDeContactoElegido().getId(), Suscripcion.CondicionSuscripcion.VIANDAS_MAX);
                }

                case SuscripcionDesperfecto suscripcionDesperfecto -> {
                    // Verifico si hay un desperfecto
                    if (!heladera.getEstado())
                        reportarEstadoSegunCondicionSuscripcion(heladeraId, suscripcion.getMedioDeContactoElegido().getId(), Suscripcion.CondicionSuscripcion.DESPERFECTO);
                }

                default -> {}

            }
        }
    }

    public void agregarVianda(Long heladeraId, Long viandaId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        Vianda vianda = viandaService.obtenerVianda(viandaId);

        if (!verificarCapacidad(heladeraId)) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.Heladera.agregarVianda_err", heladera.getNombre()));
            throw new IllegalStateException(I18n.getMessage("heladera.Heladera.agregarVianda_exception"));
        }

        heladera.getViandas().add(vianda);
        guardarHeladera(heladera);

        verificarCondiciones(heladeraId); // Verifica condiciones cuando agregamos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)

        log.log(Level.INFO, I18n.getMessage("heladera.Heladera.agregarVianda_info", vianda.getComida(), heladera.getNombre()));
    }

    public Vianda retirarVianda(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);

        if (estaVacia(heladeraId)) {
            log.log(Level.SEVERE, I18n.getMessage("heladera.Heladera.retirarVianda_err", heladera.getNombre()));
            throw new IllegalStateException(I18n.getMessage("heladera.Heladera.retirarVianda_exception"));
        }

        Vianda viandaRetirada = heladera.getViandas().removeFirst();
        guardarHeladera(heladera);

        verificarCondiciones(heladeraId); // Verifica condiciones cuando retiramos una Vianda (una de las dos únicas formas en que la cantidad de Viandas en la Heladera puede cambiar)

        log.log(Level.INFO, I18n.getMessage("heladera.Heladera.retirarVianda_info", viandaRetirada.getComida(), heladera.getNombre()));

        return viandaRetirada;
    }

    protected void verificarTempActual(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        Float tempActual = heladera.getTempActual();

        if (tempActual < heladera.getTempMin() || tempActual > heladera.getTempMax()) {
            producirAlerta(heladeraId, Alerta.TipoAlerta.TEMPERATURA);
            guardarHeladera(heladera);
        }
    }

    public void setTempActual(Long heladeraId, Float temperatura) {
        Heladera heladera = obtenerHeladera(heladeraId);
        heladera.setTempActual(temperatura);
        verificarTempActual(heladeraId);  // Siempre que setea / actualiza su temperatura, debe chequearla posteriormente
        guardarHeladera(heladera);
    }

    public void reaccionarAnteIncidente(Long heladeraId) {
        Heladera heladera = obtenerHeladera(heladeraId);
        heladera.marcarComoInactiva();
        guardarHeladera(heladera);

        verificarCondiciones(heladeraId);
    }

    public void reportarEstadoSegunCondicionSuscripcion(Long heladeraId, Long medioDeContactoId, Suscripcion.CondicionSuscripcion condicion) {   // Usa el Medio de Contacto previamente elegido por el colaborador
        Heladera heladera = obtenerHeladera(heladeraId);
        MedioDeContacto medioDeContacto = medioDeContactoService.obtenerMedioDeContacto(medioDeContactoId);

        notificadorDeEstado.notificarEstado(heladera.getId(), medioDeContacto.getId(), condicion);
    }

    public void reportarIncidente(Long incidenteId) {
        notificadorDeIncidentes.notificarIncidente(incidenteId);
    }

    public void producirAlerta(Long heladeraId, Alerta.TipoAlerta tipo) {
        Heladera heladera = obtenerHeladera(heladeraId);
        reaccionarAnteIncidente(heladeraId);  // Si una Alerta debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos
        guardarHeladera(heladera);

        Alerta alerta = new Alerta(LocalDateTime.now(), heladera, tipo);
        Long alertaId = alertaService.guardarAlerta(alerta).getId();
        reportarIncidente(alertaId);

        log.log(Level.INFO, I18n.getMessage("heladera.Heladera.producirAlerta_info", alerta.getTipo(), heladera.getNombre()));
    }

    public void producirFallaTecnica(Long heladeraId, Long colaboradorId, String descripcion, String foto) {
        Heladera heladera = obtenerHeladera(heladeraId);
        Colaborador colaborador = colaboradorService.obtenerColaborador(colaboradorId);

        reaccionarAnteIncidente(heladeraId);  // Si una Falla Técnica debe ser reportada, previamente, se marca la Heladera como inactiva y se avisa a los Colaboradores suscriptos
        guardarHeladera(heladera);

        FallaTecnica fallaTecnica = new FallaTecnica(LocalDateTime.now(), heladera, colaborador, descripcion, foto);
        Long fallaTecnicaId = fallaTecnicaService.guardarFallaTecnica(fallaTecnica).getId();
        reportarIncidente(fallaTecnicaId);

        log.log(Level.INFO, I18n.getMessage("heladera.Heladera.producirFallaTecnica_info", heladera.getNombre()));
    }
}

