package com.tp_anual.proyecto_heladeras_solidarias.domain.notificador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicador.UbicadorHeladera;

public class NotificadorDeEstado {
    private final UbicadorHeladera ubicador = new UbicadorHeladera();
    private Integer cantidadHeladeras = 10; // Esta cantidad es arbitraria, pero puede ser modificada
    
    public NotificadorDeEstado() {}

    public Integer getCantidadHeladeras() {
        return cantidadHeladeras;
    }

    public void setCantidadHeladeras(Integer vCantidadHeladeras) {
        cantidadHeladeras = vCantidadHeladeras;
    }

    public void enviarNotificacion(MedioDeContacto contacto, String asunto, String cuerpo) {
        contacto.contactar(asunto, cuerpo);
    }

    public void notificarEstado(HeladeraActiva heladera, CondicionSuscripcion condicion, MedioDeContacto contacto) {    // Usa el Medio de Contacto previamente elegido por el colaborador
        
        ArrayList<HeladeraActiva> heladerasCercanas = ubicador.obtenerHeladerasCercanasA(heladera, cantidadHeladeras);
        
        switch(condicion) {
        
        case VIANDAS_MIN -> {
            // Obtengo la Heladera más llena
            HeladeraActiva heladeraMasLlena = ubicador.obtenerHeladeraMasLlena(heladerasCercanas);

            enviarNotificacion(
            contacto,
            "La heladera " + heladera.getNombre() + " se está vaciando.",
            "La heladera en cuestión tiene " + heladera.viandasActuales() + " viandas disponibles. " +
            "Sería conveniente traer viandas de la heladera " + heladeraMasLlena.getNombre() + ", " +
            "que está situada en " + heladeraMasLlena.getUbicacion().getDireccion());
        }
        
        case VIANDAS_MAX -> {
            // Obtengo la Heladera menos llena
            HeladeraActiva heladeraMenosLlena = ubicador.obtenerHeladeraMenosLlena(heladerasCercanas);

            enviarNotificacion(
            contacto,
            "La heladera " + heladera.getNombre() + " está casi llena.",
            "Faltan " + (heladera.getCapacidad() - heladera.viandasActuales()) + " viandas para llenar la heladera en cuestión. " +
            "Sería conveniente llevar viandas a la heladera " + heladeraMenosLlena.getNombre() + ", " +
            "que está situada en " + heladeraMenosLlena.getUbicacion().getDireccion());
        }

        case DESPERFECTO -> {

            enviarNotificacion(
            contacto,
            "La heladera "+ heladera.getNombre() + " ha sufrido un desperfecto.",
            "Las viandas deben ser trasladadas de inmediato a alguna de estas Heladeras: \n" + 
            obtenerNombresYDireccionesDe(heladerasCercanas));
        }

        default -> {}

        }
    }

    public String obtenerNombresYDireccionesDe(ArrayList<HeladeraActiva> heladeras) {
        HeladeraActiva heladera1 = heladeras.removeFirst();
        String nombresYDirecciones = "Nombre: " + heladera1.getNombre() + " | Dirección: " + heladera1.getUbicacion().getDireccion() + "\n"; 
        
        for (HeladeraActiva heladera : heladeras) {
            nombresYDirecciones += "Nombre: " + heladera.getNombre() + " | Dirección: " + heladera.getUbicacion().getDireccion() + "\n";
        }

        return nombresYDirecciones;
    }
}
