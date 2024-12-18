package com.tp_anual.proyecto_heladeras_solidarias.service.notificador;

import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.service.contacto.MedioDeContactoServiceSelector;
import com.tp_anual.proyecto_heladeras_solidarias.service.ubicador.UbicadorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class NotificadorDeEstado extends Notificador {

    private final UbicadorHeladera ubicadorHeladera;

    @Getter
    @Setter
    private Integer cantidadHeladeras;

    private final I18nService i18nService;
    
    public NotificadorDeEstado(MedioDeContactoServiceSelector vMedioDeContactoServiceSelector, UbicadorHeladera vUbicadorHeladera, I18nService vI18nService) {
        super(vMedioDeContactoServiceSelector);
        ubicadorHeladera = vUbicadorHeladera;
        cantidadHeladeras = 10; // Esta cantidad es arbitraria, pero puede ser modificada

        i18nService = vI18nService;
    }

    public void notificarEstado(Heladera heladera, MedioDeContacto medioDeContacto, CondicionSuscripcion condicion) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        List<Heladera> heladerasCercanas = ubicadorHeladera.obtenerHeladerasCercanasA(heladera, cantidadHeladeras);
        
        switch(condicion) {

            case VIANDAS_MIN -> {

                if (heladerasCercanas == null || heladerasCercanas.isEmpty())
                    return;

                // Obtengo la Heladera más llena
                Heladera heladeraMasLlena = ubicadorHeladera.obtenerHeladeraMasLlena(heladerasCercanas);

                enviarNotificacion(
                medioDeContacto,
                "La heladera " + heladera.getNombre() + " se est� vaciando!",
                "La heladera " + heladera.getNombre() + " tiene " + heladera.viandasActuales() + " viandas disponibles.\\nSer�a conveniente traer viandas de la heladera " + heladeraMasLlena.getNombre() + ", que est� situada en " + heladeraMasLlena.getUbicacion().getDireccion() + ".");
            }

            case VIANDAS_MAX -> {

                if (heladerasCercanas == null || heladerasCercanas.isEmpty())
                    return;

                // Obtengo la Heladera menos llena
                Heladera heladeraMenosLlena = ubicadorHeladera.obtenerHeladeraMenosLlena(heladerasCercanas);

                enviarNotificacion(
                medioDeContacto,
                "La heladera " + heladera.getNombre() + " est� casi llena!",
                "Faltan " + (heladera.getCapacidad() - heladera.viandasActuales()) + " viandas para llenar la heladera " + heladera.getNombre() + ".\\nSer�a conveniente llevar viandas a la heladera " + heladeraMenosLlena.getNombre() + ", que est� situada en " + heladeraMenosLlena.getUbicacion().getDireccion() + ".");
            }

            case DESPERFECTO -> {

                if (heladerasCercanas == null || heladerasCercanas.isEmpty()) {
                    enviarNotificacion(
                            medioDeContacto,
                            "La heladera " + heladera.getNombre() + " ha sufrido un desperfecto!",
                            "Las viandas deben ser trasladadas de inmediato, pero al no haber heladeras cercanas activas, le pedimos si puede hacerse cargo por su cuenta de las que pueda!");

                    return;
                }

                enviarNotificacion(
                medioDeContacto,
                "La heladera " + heladera.getNombre() + " ha sufrido un desperfecto!",
                "Las viandas deben ser trasladadas de inmediato a alguna de estas heladera: \\n" + obtenerNombresYDireccionesDe(heladerasCercanas));
            }

            default -> {}
        }
    }

    public String obtenerNombresYDireccionesDe(List<Heladera> heladeras) {
        Heladera heladera1 = heladeras.removeFirst();
        String nombresYDirecciones = i18nService.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_1", heladera1.getNombre(), heladera1.getUbicacion().getDireccion());
        
        for (Heladera heladera : heladeras) {
            nombresYDirecciones += i18nService.getMessage("notificador.NotificadorDeEstado.obtenerNombresYDireccionesDe_value_heladera_i", heladera.getNombre(), heladera.getUbicacion().getDireccion());
        }

        return nombresYDirecciones;
    }
}