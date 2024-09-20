package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionDesperfecto;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMax;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMin;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorNula;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Humano")
@Log
@Getter
@Setter
public class ColaboradorHumano extends Colaborador {    // Implementa una Interfaz "ColaboradorHumanoObserver" a nivel conceptual
    
    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    protected TarjetaColaborador tarjeta;
    
    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    protected final ArrayList<Suscripcion> suscripciones;   // Será una Suscripción por Heladera

    public ColaboradorHumano(PersonaFisica vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        super(vPersona, vDomicilio, vMediosDeContacto, vContribuciones, vBeneficiosAdquiridos, vPuntos);
        
        creatorsPermitidos = new HashSet<>();
        creatorsPermitidos.add(DistribucionViandasCreator.class);
        creatorsPermitidos.add(DonacionDineroCreator.class);
        creatorsPermitidos.add(DonacionViandaCreator.class);
        creatorsPermitidos.add(RegistroDePersonaEnSituacionVulnerableCreator.class);

        tarjeta = new TarjetaColaboradorNula();
        suscripciones = new ArrayList<>();
    }

    @Override
    public PersonaFisica getPersona() {
        return (PersonaFisica) persona;
    }

    private void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    private void eliminarSuscripcion(Suscripcion suscripcion) {
        suscripciones.remove(suscripcion);
    }
    
    public Suscripcion suscribirse(HeladeraActiva heladeraObjetivo, MedioDeContacto medioDeContacto, CondicionSuscripcion condicionSuscripcion, Integer valor) {

        Suscripcion suscripcion;

        switch(condicionSuscripcion) {
        
            case VIANDAS_MIN -> suscripcion = new SuscripcionViandasMin(this, heladeraObjetivo, medioDeContacto, valor);
            
            case VIANDAS_MAX -> suscripcion = new SuscripcionViandasMax(this, heladeraObjetivo, medioDeContacto, valor);
            
            case DESPERFECTO -> suscripcion = new SuscripcionDesperfecto(this, heladeraObjetivo, medioDeContacto);
            
            default -> {
                log.log(Level.SEVERE, I18n.getMessage("heladera.HeladerNula.getUbicacion_err", persona.getNombre(2)));
                throw new IllegalArgumentException(I18n.getMessage("colaborador.ColaboradorHumano.suscribirse_exception"));
            }
            
            }

        suscripcion.darDeAlta();
        agregarSuscripcion(suscripcion);
        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.agregarSuscripcion_info", persona.getNombre(2), suscripcion.getHeladera().getNombre()));

        return suscripcion;
    }

    public void modificarSuscripcion(Suscripcion suscripcion, Integer nuevoValor) {
        
        switch(suscripcion) {
        
        case SuscripcionViandasMin suscripcionViandasMin -> suscripcionViandasMin.setViandasDisponiblesMin(nuevoValor);
        
        case SuscripcionViandasMax suscripcionViandasMax -> suscripcionViandasMax.setViandasParaLlenarMax(nuevoValor);
        
        default -> {}
        
        }

        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.modificarSuscripcion_info", suscripcion.getHeladera().getNombre(), persona.getNombre(2)));
    }

    public void cancelarSuscripcion(Suscripcion suscripcion) {
        suscripcion.darDeBaja();
        eliminarSuscripcion(suscripcion);
        log.log(Level.INFO, I18n.getMessage("colaborador.ColaboradorHumano.cancelarSuscripcion_info", suscripcion.getHeladera().getNombre(), persona.getNombre(2)));
    }
}